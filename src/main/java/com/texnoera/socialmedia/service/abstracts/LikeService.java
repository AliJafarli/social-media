package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.request.LikeRequest;
import com.texnoera.socialmedia.model.response.like.LikeResponse;

import java.util.List;

public interface LikeService {

    List<LikeResponse> getAllByPost(Integer postId);
    List<LikeResponse> getAllByUser(Integer userId);
    boolean isLiked(Integer userId, Integer postId);
    LikeResponse add(LikeRequest likeRequest);
    void delete(LikeRequest likeRequest);
}
