package com.texnoera.socialmedia.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequest {
    private Long userId;
    private Long followingId;

}
