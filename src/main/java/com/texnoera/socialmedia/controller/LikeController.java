package com.texnoera.socialmedia.controller;


import com.texnoera.socialmedia.model.request.LikeRequest;
import com.texnoera.socialmedia.model.response.like.LikeResponse;
import com.texnoera.socialmedia.service.abstracts.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/add")
    public ResponseEntity<LikeResponse> addLike(@Valid @RequestBody LikeRequest likeRequest) {
        log.info("Received request to add like for user {} on post {}", likeRequest.getUserId(), likeRequest.getPostId());
        LikeResponse likeResponse = likeService.add(likeRequest);
        log.info("Successfully added like for user {} on post {}", likeRequest.getUserId(), likeRequest.getPostId());
        return new ResponseEntity<>(likeResponse, HttpStatus.CREATED);
    }

    @GetMapping("get-all-by-post/{postId}")
    public ResponseEntity<List<LikeResponse>> getAllByPostId(@PathVariable Long postId) {
        log.info("Received request to get all likes for post {}", postId);
        List<LikeResponse> likes = likeService.getAllByPost(postId);
        log.info("Returning {} likes for post {}", likes.size(), postId);
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

    @GetMapping("get-all-by-user/{userId}")
    public ResponseEntity<List<LikeResponse>> getAllByUserId(@PathVariable Long userId) {
        log.info("Received request to get all likes for user {}", userId);
        List<LikeResponse> likes = likeService.getAllByUser(userId);
        log.info("Returning {} likes for user {}", likes.size(), userId);
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

    @GetMapping("/is-liked")
    public ResponseEntity<Boolean> isLiked(@RequestParam Long userId, @RequestParam Long postId) {
        log.info("Received request to check if user {} has liked post {}", userId, postId);
        boolean isLiked = likeService.isLiked(userId, postId);
        log.info("User {} has {}liked post {}", userId, isLiked ? "" : "not ", postId);
        return new ResponseEntity<>(isLiked, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteLike(@RequestBody LikeRequest likeRequest) {
        log.info("Received request to delete like for user {}", likeRequest.getUserId());
        likeService.delete(likeRequest);
        log.info("Successfully deleted like for user {}", likeRequest.getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
