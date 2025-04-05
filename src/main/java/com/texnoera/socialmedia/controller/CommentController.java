package com.texnoera.socialmedia.controller;


import com.texnoera.socialmedia.model.request.CommentAddRequest;
import com.texnoera.socialmedia.model.response.comment.CommentGetResponse;
import com.texnoera.socialmedia.service.abstracts.CommentService;
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
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/get-all")
    public ResponseEntity<List<CommentGetResponse>> getAllComments() {
        log.info("Received request to get all comments");
        List<CommentGetResponse> comments = commentService.getAll();
        log.info("Retrieved {} comments", comments.size());
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/get-all-by-post/{postId}")
    public ResponseEntity<List<CommentGetResponse>> getAllCommentsByPostId(@PathVariable Integer postId) {
        log.info("Received request to get all comments by post ID {}", postId);
        List<CommentGetResponse> comments = commentService.getAllByPost(postId);
        log.info("Retrieved {} comments for post ID: {}", comments.size(), postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/get-all-by-user/{userId}")
    public ResponseEntity<List<CommentGetResponse>> getAllCommentsByUserId(@PathVariable Integer userId) {
        log.info("Received request to get comments for user ID: {}", userId);
        List<CommentGetResponse> comments = commentService.getAllByUser(userId);
        log.info("Retrieved {} comments for user ID: {}", comments.size(), userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CommentGetResponse> addComment(@RequestBody @Valid CommentAddRequest commentAddRequest) {
        log.info("Received request to add comment: {}", commentAddRequest);
        CommentGetResponse commentGetResponse = commentService.add(commentAddRequest);
        log.info("Successfully added comment with ID: {}", commentGetResponse.getId());
        return new ResponseEntity<>(commentGetResponse, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        log.info("Received request to delete comment with ID: {}", id);
        commentService.delete(id);
        log.info("Successfully deleted comment with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
