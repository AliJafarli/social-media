package com.texnoera.socialmedia.controller;

import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import com.texnoera.socialmedia.service.abstracts.PostService;
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
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/get-all-posts")
    public ResponseEntity<List<PostGetResponse>> getAllPosts() {
        return new ResponseEntity<>(postService.getAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostGetResponse> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getResponseById(id), HttpStatus.OK);
    }

    @GetMapping("/get-all-by-user/{userId}")
    public ResponseEntity<List<PostGetResponse>> getAllPostsByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(postService.getAllByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/get-by-user-following/{userId}")
    public ResponseEntity<List<PostGetResponse>> getPostsByUserFollowing(@PathVariable Long userId) {
        return new ResponseEntity<>(postService.getByUserFollowing(userId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createPost(@RequestBody PostAddRequest postAddRequest) {
        Long postId = postService.add(postAddRequest);
        return new ResponseEntity<>(postId, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
