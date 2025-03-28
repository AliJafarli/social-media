package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.request.FollowRequest;
import com.texnoera.socialmedia.model.response.follow.FollowResponse;

public interface FollowService {

    FollowResponse add(FollowRequest followRequest);
    void delete(FollowRequest followRequest);
}
