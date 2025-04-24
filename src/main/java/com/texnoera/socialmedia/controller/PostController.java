package com.texnoera.socialmedia.controller;

import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.page.PageResponse;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;
import com.texnoera.socialmedia.service.abstracts.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;


@Log4j2
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<PageResponse<PostGetResponse>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
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
    public ResponseEntity<PageResponse<PostGetResponse>> getAllPostsByUserId(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        log.info("GET /posts/get-all-by-user/{} called with page={}, size={}, sortBy={}, direction={}", userId, page, size, sortBy, direction);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        PageResponse<PostGetResponse> response = postService.getAllByUser(userId, pageable);

        log.info("Returning {} posts for user ID {}, page {}/{}",
                response.getContent().size(), userId, response.getPage(), response.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/get-by-user-following/{userId}")
    public ResponseEntity<PageResponse<PostGetResponse>> getPostsByUserFollowing(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        log.info("GET /posts/get-by-user-following/{} called with page={}, size={}, sortBy={}, direction={}", userId, page, size, sortBy, direction);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        PageResponse<PostGetResponse> response = postService.getByUserFollowing(userId, pageable);

        log.info("Returning {} posts from followed users for user ID {}, page {}/{}",
                response.getContent().size(), userId, response.getPage(), response.getTotalPages());

        return ResponseEntity.ok(response);
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
