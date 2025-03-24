package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.mapper.UserMapper;
import com.texnoera.socialmedia.model.entity.Follow;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.UserAddRequest;
import com.texnoera.socialmedia.model.request.UserUpdateRequest;
import com.texnoera.socialmedia.model.response.user.UserFollowingResponse;
import com.texnoera.socialmedia.model.response.user.UserResponse;
import com.texnoera.socialmedia.repository.FollowRepository;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FollowRepository followRepository;

    @Override
    public List<UserResponse> getAll() {

        return userMapper.usersToResponses(userRepository.findAll());
    }

    @Override
    public UserResponse getResponseById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return userMapper.userToResponse(user);
    }

    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return userMapper.userToResponse(user);
    }

    @Override
    public List<UserFollowingResponse> getUserFollowing(Long userId) {
        return userMapper.followsToFollowingResponses(followRepository.findAllByUser_Id(userId));
    }

    @Override
    public boolean isFollowing(Long userId, Long followingId) {
        Optional<Follow> follow = followRepository.findByUser_IdAndFollowing_Id(userId, followingId);
        return follow.isPresent();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("user not found"));
    }

    @Override
    public void add(UserAddRequest userAddRequest) {
        User user = userMapper.requestToUser(userAddRequest);
        userRepository.save(user);
    }


    @Override
    public UserResponse update(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("user not found"));
        userMapper.update(user, userUpdateRequest);
        return userMapper.userToResponse(userRepository.save(user));

    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
