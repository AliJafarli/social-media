package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.UserAddRequest;
import com.texnoera.socialmedia.model.response.user.UserFollowingResponse;
import com.texnoera.socialmedia.model.response.user.UserResponse;
import com.texnoera.socialmedia.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public List<UserResponse> getAll() {
        return List.of();
    }

    @Override
    public UserResponse getResponseById(Long id) {
        return null;
    }

    @Override
    public UserResponse getByEmail(String email) {
        return null;
    }

    @Override
    public List<UserFollowingResponse> getUserFollowing(Long userId) {
        return List.of();
    }

    @Override
    public boolean isFollowing(Long userId, Long followingId) {
        return false;
    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public void add(UserAddRequest userAddRequest) {

    }

    @Override
    public void delete(Long id) {

    }
}
