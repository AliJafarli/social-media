package com.texnoera.socialmedia.controller;

import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.page.PageResponse;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;
import com.texnoera.socialmedia.service.abstracts.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Log4j2
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/all")
    public ResponseEntity<PageResponse<PostGetResponse>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "created") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        log.info("GET /posts/all called with page={}, size={}, sortBy={}, direction={}", page, size, sortBy, direction);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        PageResponse<PostGetResponse> response = postService.getAll(pageable);

        log.info("Returning {} posts, page {}/{}", response.getContent().size(), response.getPage(), response.getTotalPages());

        return ResponseEntity.ok(response);
    }



    @GetMapping("/{id}")
    public ResponseEntity<PostGetResponse> getPostById(@PathVariable Integer id) {
        log.info("Received request to get post with ID: {}", id);
        PostGetResponse post = postService.getResponseById(id);
        log.info("Successfully retrieved post with ID: {}", id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/get-all-by-user/{userId}")
    public ResponseEntity<List<PostGetResponse>> getAllPostsByUserId(@PathVariable Integer userId) {
        log.info("Received request to get posts for user with ID: {}", userId);
        List<PostGetResponse> posts = postService.getAllByUser(userId);
        log.info("Retrieved {} posts for user ID: {}", posts.size(), userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/get-by-user-following/{userId}")
    public ResponseEntity<List<PostGetResponse>> getPostsByUserFollowing(@PathVariable Integer userId) {
        log.info("Received request to get posts for user following user with ID: {}", userId);
        List<PostGetResponse> posts = postService.getByUserFollowing(userId);
        log.info("Retrieved {} posts for user following user ID: {}", posts.size(), userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<IamResponse<PostGetResponse>> createPost(@RequestBody @Valid PostAddRequest postAddRequest, Principal principal) {
        log.info("Received request to create post with data: {}", postAddRequest);
        IamResponse<PostGetResponse> response = postService.add(postAddRequest, principal.getName());
        log.info("Successfully created post");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        log.info("Received request to delete post with ID: {}", id);
        postService.delete(id);
        log.info("Successfully deleted post with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
