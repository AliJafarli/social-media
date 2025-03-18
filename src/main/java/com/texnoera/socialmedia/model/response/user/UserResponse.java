package com.texnoera.socialmedia.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private List<UserFollowerResponse> followers;
    private List<UserFollowingResponse> followings;
}
