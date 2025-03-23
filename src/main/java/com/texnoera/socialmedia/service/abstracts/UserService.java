package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.UserAddRequest;
import com.texnoera.socialmedia.model.request.UserUpdateRequest;
import com.texnoera.socialmedia.model.response.user.UserFollowingResponse;
import com.texnoera.socialmedia.model.response.user.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAll();
    UserResponse getResponseById(Long id);
    UserResponse getByEmail(String email);
    List<UserFollowingResponse> getUserFollowing(Long userId);
    boolean isFollowing(Long userId, Long followingId);
    User getById(Long id);
    void add(UserAddRequest userAddRequest);
    void delete(Long id);
    UserResponse update(Long id, UserUpdateRequest userUpdateRequest);
}
