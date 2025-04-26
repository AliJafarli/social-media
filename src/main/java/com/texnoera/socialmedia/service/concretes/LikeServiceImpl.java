package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.mapper.LikeMapper;
import com.texnoera.socialmedia.model.entity.Like;
import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.LikeRequest;
import com.texnoera.socialmedia.model.response.like.LikeResponse;
import com.texnoera.socialmedia.repository.LikeRepository;
import com.texnoera.socialmedia.repository.PostRepository;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.service.abstracts.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Log4j2
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

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

        User user = userRepository.findById(likeRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(likeRequest.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        likeRepository.save(like);

        return likeMapper.likeToPostLikeResponse(like);

    }

    @Transactional
    @Override
    public void delete(LikeRequest likeRequest) {
        likeRepository.findByUser_IdAndPost_Id(likeRequest.getUserId(), likeRequest.getPostId())
                .ifPresentOrElse(
                        likeRepository::delete,
                        () -> log.warn("Like not found for user {} and post {}", likeRequest.getUserId(), likeRequest.getPostId())
                );
    }
}
