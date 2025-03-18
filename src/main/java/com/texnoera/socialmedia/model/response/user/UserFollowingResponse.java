package com.texnoera.socialmedia.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowingResponse {

    private Long userId;
    private String username;
}
