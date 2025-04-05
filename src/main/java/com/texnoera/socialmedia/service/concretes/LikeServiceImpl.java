package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.mapper.LikeMapper;
import com.texnoera.socialmedia.model.entity.Like;
import com.texnoera.socialmedia.model.request.LikeRequest;
import com.texnoera.socialmedia.model.response.like.LikeResponse;
import com.texnoera.socialmedia.repository.LikeRepository;
import com.texnoera.socialmedia.service.abstracts.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;

    @Override
    public List<LikeResponse> getAllByPost(Integer postId) {
        List<Like> likes = likeRepository.findAllByPost_Id(postId);
        return likeMapper.likesToLikeResponses(likes);
    }

    @Override
    public List<LikeResponse> getAllByUser(Integer userId) {
        List<Like> likes = likeRepository.findAllByUser_Id(userId);
        return likeMapper.likesToLikeResponses(likes);
    }

    @Override
    public boolean isLiked(Integer userId, Integer postId) {
        Optional<Like> like = likeRepository.findByUser_IdAndPost_Id(userId, postId);
        return like.isPresent();
    }

    @Override
    public LikeResponse add(LikeRequest likeRequest) {
        if (isLiked(likeRequest.getUserId(), likeRequest.getPostId())) {
            throw new RuntimeException("Already liked");
        }
        Like like = likeMapper.requestToLike(likeRequest);
        likeRepository.save(like);
        return likeMapper.likeToPostLikeResponse(like);

    }

    @Override
    public void delete(LikeRequest likeRequest) {
        Optional<Like> like = likeRepository.
                findByUser_IdAndPost_Id(likeRequest.getUserId(), likeRequest.getPostId());

            like.ifPresent(likeRepository::delete);


    }
}
