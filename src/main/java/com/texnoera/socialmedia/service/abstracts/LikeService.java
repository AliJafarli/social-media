package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.request.LikeRequest;
import com.texnoera.socialmedia.model.response.like.LikeResponse;

import java.util.List;

public interface LikeService {

    List<LikeResponse> getAllByPost(Long postId);
    List<LikeResponse> getAllByUser(Long userId);
    boolean isLiked(Long userId, Long postId);
    LikeResponse add(LikeRequest likeRequest);
    void delete(LikeRequest likeRequest);
}
