package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.request.FollowRequest;

public interface FollowService {

    void add(FollowRequest followRequest);
    void delete(FollowRequest followRequest);
}
