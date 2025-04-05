package com.texnoera.socialmedia.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowingResponse {

    private Integer userId;
    private String username;
}
