package com.texnoera.socialmedia.model.response.follow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponse {

    private Long id;
    private Long followerId;
    private Long followingId;
    private String followerUsername;
    private String followingUsername;
}
