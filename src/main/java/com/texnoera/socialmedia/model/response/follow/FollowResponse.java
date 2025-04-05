package com.texnoera.socialmedia.model.response.follow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponse {

    private Integer id;
    private Integer followerId;
    private Integer followingId;
    private String followerUsername;
    private String followingUsername;
}
