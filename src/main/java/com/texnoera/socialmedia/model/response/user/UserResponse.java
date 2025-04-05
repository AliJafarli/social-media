package com.texnoera.socialmedia.model.response.user;

import com.texnoera.socialmedia.model.response.role.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer id;
    private String username;
    private String email;
    private List<UserFollowerResponse> followers;
    private List<UserFollowingResponse> followings;
    private List<RoleResponse> roles;
}
