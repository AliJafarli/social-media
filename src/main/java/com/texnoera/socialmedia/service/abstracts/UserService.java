package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.UserAddRequest;
import com.texnoera.socialmedia.model.request.UserUpdateRequest;
import com.texnoera.socialmedia.model.response.page.PageResponse;
import com.texnoera.socialmedia.model.response.user.UserFollowingResponse;
import com.texnoera.socialmedia.model.response.user.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    PageResponse<UserResponse> getAll(Pageable pageable);
    UserResponse getResponseById(Integer id);
    UserResponse getByEmail(String email);
    List<UserFollowingResponse> getUserFollowing(Integer userId);
    boolean isFollowing(Integer userId, Integer followingId);
    User getById(Integer id);
    UserResponse add(UserAddRequest userAddRequest);
    void delete(Integer id);
    UserResponse update(Integer id, UserUpdateRequest userUpdateRequest);
}
