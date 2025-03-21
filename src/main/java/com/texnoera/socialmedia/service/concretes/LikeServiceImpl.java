package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.model.request.LikeRequest;
import com.texnoera.socialmedia.model.response.like.LikeResponse;
import com.texnoera.socialmedia.service.abstracts.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    @Override
    public List<LikeResponse> getAllByPost(Long postId) {
        return List.of();
    }

    @Override
    public List<LikeResponse> getAllByUser(Long userId) {
        return List.of();
    }

    @Override
    public boolean isLiked(Long userId, Long postId) {
        return false;
    }

    @Override
    public void add(LikeRequest likeRequest) {

    }

    @Override
    public void delete(LikeRequest likeRequest) {

    }
}
