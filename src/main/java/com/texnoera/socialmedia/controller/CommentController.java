package com.texnoera.socialmedia.controller;


import com.texnoera.socialmedia.model.request.CommentAddRequest;
import com.texnoera.socialmedia.model.response.comment.CommentGetResponse;
import com.texnoera.socialmedia.model.response.page.PageResponse;
import com.texnoera.socialmedia.service.abstracts.CommentService;
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


@Log4j2
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/get-all")
    public ResponseEntity<PageResponse<CommentGetResponse>> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        log.info("GET /comments/get-all called with page={}, size={}, sortBy={}, direction={}", page, size, sortBy, direction);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        PageResponse<CommentGetResponse> comments = commentService.getAll(pageable);
        log.info("Returning {} comments, page {}/{}", comments.getContent().size(), comments.getPage(), comments.getTotalPages());

        return ResponseEntity.ok(comments);
    }

    @GetMapping("/get-all-by-post/{postId}")
    public ResponseEntity<PageResponse<CommentGetResponse>> getAllCommentsByPostId(
            @PathVariable Integer postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        log.info("GET /comments/get-all-by-post/{} called with page={}, size={}, sortBy={}, direction={}", postId, page, size, sortBy, direction);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        PageResponse<CommentGetResponse> comments = commentService.getAllByPost(postId, pageable);
        log.info("Returning {} comments for post ID {}, page {}/{}", comments.getContent().size(), postId, comments.getPage(), comments.getTotalPages());

        return ResponseEntity.ok(comments);
    }

    @GetMapping("/get-all-by-user/{userId}")
    public ResponseEntity<PageResponse<CommentGetResponse>> getAllCommentsByUserId(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        log.info("GET /comments/get-all-by-user/{} called with page={}, size={}, sortBy={}, direction={}", userId, page, size, sortBy, direction);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        PageResponse<CommentGetResponse> comments = commentService.getAllByUser(userId, pageable);
        log.info("Returning {} comments for user ID {}, page {}/{}", comments.getContent().size(), userId, comments.getPage(), comments.getTotalPages());

        return ResponseEntity.ok(comments);
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
