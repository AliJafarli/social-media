package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.exception.DataExistException;
import com.texnoera.socialmedia.exception.NotFoundException;
import com.texnoera.socialmedia.exception.constants.ExceptionConstants;
import com.texnoera.socialmedia.mapper.UserMapper;
import com.texnoera.socialmedia.model.entity.RefreshToken;
import com.texnoera.socialmedia.model.entity.Role;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.RegistrationUserRequest;
import com.texnoera.socialmedia.model.request.UserAddRequest;
import com.texnoera.socialmedia.model.request.UserUpdateRequest;
import com.texnoera.socialmedia.model.response.page.PageResponse;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;
import com.texnoera.socialmedia.model.response.user.UserFollowingResponse;
import com.texnoera.socialmedia.model.response.user.UserProfileResponse;
import com.texnoera.socialmedia.model.response.user.UserResponse;
import com.texnoera.socialmedia.repository.FollowRepository;
import com.texnoera.socialmedia.repository.RefreshTokenRepository;
import com.texnoera.socialmedia.repository.RoleRepository;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.security.JwtTokenProvider;
import com.texnoera.socialmedia.security.enums.SocialMediaUserRole;
import com.texnoera.socialmedia.security.validation.AccessValidator;
import com.texnoera.socialmedia.service.abstracts.RefreshTokenService;
import com.texnoera.socialmedia.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AccessValidator accessValidator;
    private final RefreshTokenRepository refreshTokenRepository;

    public PageResponse<UserResponse> getAll(Pageable pageable) {
        log.debug("Fetching all users with pageable: {}", pageable);
        Page<User> userPage = userRepository.findAll(pageable);
        log.debug("Found {} users in DB", userPage.getContent().size());
        List<UserResponse> userResponses = userMapper.usersToResponses(userPage.getContent());

        return new PageResponse<>(
                userResponses,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast()
        );
    }

    @Override
    public UserResponse getResponseById(Integer id) {
        log.debug("Getting user response by ID: {}", id);
        User user = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> {
            log.warn("User not found by ID: {}", id);
            return new NotFoundException(ExceptionConstants.USER_NOT_FOUND_BY_ID.getMessage(id));
        });
        return userMapper.userToResponse(user);
    }

    @Override
    public UserResponse getByEmail(String email) {
        log.debug("Getting user by email: {}", email);
        User user = userRepository.findUserByEmailAndDeletedFalse(email)
                .orElseThrow(() -> {
                    log.warn("User not found with email: {}", email);
                    return new NotFoundException(ExceptionConstants.USER_NOT_FOUND_BY_EMAIL.getMessage(email));
                });

        return userMapper.userToResponse(user);
    }

    @Override
    public List<UserFollowingResponse> getUserFollowing(Integer userId) {
        log.debug("Fetching following list for user ID: {}", userId);
        return userMapper.followsToFollowingResponses(followRepository.findAllByUser_Id(userId));
    }

    @Override
    public boolean isFollowing(Integer userId, Integer followingId) {
        log.debug("Checking if user {} follows user {}", userId, followingId);
        boolean result = followRepository.findByUser_IdAndFollowing_Id(userId, followingId).isPresent();
        log.debug("Is following result: {}", result);
        return result;
    }

    @Override
    public User getById(Integer id) {
        log.debug("Fetching user entity by ID: {}", id);

        return userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("User not found by ID: {}", id);
                    return new NotFoundException(ExceptionConstants.USER_NOT_FOUND_BY_ID.getMessage(id));
                });
    }


    @Override
    @Transactional
    public IamResponse<UserResponse> add(UserAddRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DataExistException(ExceptionConstants.EMAIL_ALREADY_EXISTS.getMessage(request.getEmail()));
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DataExistException(ExceptionConstants.USERNAME_ALREADY_EXISTS.getMessage(request.getUsername()));
        }

        Role userRole = roleRepository.findByName(SocialMediaUserRole.USER.getRole())
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.USER_ROLE_NOT_FOUND.getUserMessage()));

        User user = userMapper.requestToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        return IamResponse.createSuccessful(userMapper.userToResponse(savedUser));

    }


    @Override
    public UserResponse update(Integer id, UserUpdateRequest request) {
        log.info("Updating user ID: {}", id);
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
        User updatedUser = userRepository.save(user);

        log.info("User updated: id={}, username={}", updatedUser.getId(), updatedUser.getUsername());
        return userMapper.userToResponse(updatedUser);

    }

    @Transactional
    @Override
    public void delete(Integer userId) {
        log.info("Deleting user ID: {}", userId);
        accessValidator.validateAdminOrOwnerAccess(userId);
        refreshTokenRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
        log.info("User deleted successfully: ID={}", userId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Loading user by username (email): {}", email);
        return getUserDetails(email, userRepository);
    }

    static UserDetails getUserDetails(String email, UserRepository userRepository) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException(ExceptionConstants.NOT_FOUND_EXCEPTION.getUserMessage()));
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        log.info("User login recorded: {}", user.getEmail());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
