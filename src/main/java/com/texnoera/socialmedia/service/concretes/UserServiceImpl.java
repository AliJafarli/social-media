package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.exception.DataExistException;
import com.texnoera.socialmedia.exception.NotFoundException;
import com.texnoera.socialmedia.exception.constants.ExceptionConstants;
import com.texnoera.socialmedia.mapper.UserMapper;
import com.texnoera.socialmedia.model.entity.Follow;
import com.texnoera.socialmedia.model.entity.Role;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.UserAddRequest;
import com.texnoera.socialmedia.model.request.UserUpdateRequest;
import com.texnoera.socialmedia.model.response.user.UserFollowingResponse;
import com.texnoera.socialmedia.model.response.user.UserResponse;
import com.texnoera.socialmedia.repository.FollowRepository;
import com.texnoera.socialmedia.repository.RoleRepository;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.security.enums.SocialMediaUserRole;
import com.texnoera.socialmedia.security.validation.AccessValidator;
import com.texnoera.socialmedia.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AccessValidator accessValidator;

    @Override
    public List<UserResponse> getAll() {

        return userMapper.usersToResponses(userRepository.findAll());
    }

    @Override
    public UserResponse getResponseById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        return userMapper.userToResponse(user);
    }

    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return userMapper.userToResponse(user);
    }

    @Override
    public List<UserFollowingResponse> getUserFollowing(Integer userId) {
        return userMapper.followsToFollowingResponses(followRepository.findAllByUser_Id(userId));
    }

    @Override
    public boolean isFollowing(Integer userId, Integer followingId) {
        Optional<Follow> follow = followRepository.findByUser_IdAndFollowing_Id(userId, followingId);
        return follow.isPresent();
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("user not found"));
    }

    @Override
    public UserResponse add(UserAddRequest userAddRequest) {


        Role userRole = roleRepository.findByName(SocialMediaUserRole.USER.getRole())
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.NOT_FOUND_EXCEPTION.getUserMessage()));

        User user = userMapper.requestToUser(userAddRequest);
        user.setPassword(passwordEncoder.encode(userAddRequest.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.userToResponse(user);
    }


    @Override
    public UserResponse update(Integer id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ExceptionConstants.USER_NOT_FOUND_BY_ID.getMessage(id)));

        accessValidator.validateAdminOrOwnerAccess(id);

        if (!user.getUsername().equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
            throw new DataExistException(ExceptionConstants.USERNAME_ALREADY_EXISTS.getMessage(request.getUsername()));
        }

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new DataExistException(ExceptionConstants.EMAIL_ALREADY_EXISTS.getMessage(request.getEmail()));
        }
        userMapper.update(user, request);
        return userMapper.userToResponse(userRepository.save(user));

    }

    @Override
    public void delete(Integer id) {
        accessValidator.validateAdminOrOwnerAccess(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getUserDetails(email, userRepository);
    }

    static UserDetails getUserDetails(String email, UserRepository userRepository) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException(ExceptionConstants.NOT_FOUND_EXCEPTION.getUserMessage()));
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
