package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.mapper.CommentMapper;
import com.texnoera.socialmedia.model.entity.Comment;
import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.CommentAddRequest;
import com.texnoera.socialmedia.model.request.CommentUpdateRequest;
import com.texnoera.socialmedia.model.response.comment.CommentGetResponse;
import com.texnoera.socialmedia.model.response.page.PageResponse;
import com.texnoera.socialmedia.repository.CommentRepository;
import com.texnoera.socialmedia.repository.PostRepository;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.service.abstracts.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public CommentGetResponse add(CommentAddRequest commentAddRequest) {
        User user = userRepository.findById(commentAddRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        Post post = postRepository.findById(commentAddRequest.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = new Comment();
        comment.setCommentText(commentAddRequest.getCommentText());
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);

        Comment saved = commentRepository.findById(comment.getId())
                .orElseThrow(() -> new RuntimeException("Comment not found after save"));

        return commentMapper.commentToResponse(saved);
    }

    @Override
    public PageResponse<CommentGetResponse> getAll(Pageable pageable) {
        Page<Comment> commentPage = commentRepository.findAll(pageable);
        List<CommentGetResponse> content = commentPage.getContent().stream()
                .map(commentMapper::commentToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                commentPage.getNumber(),
                commentPage.getSize(),
                commentPage.getTotalElements(),
                commentPage.getTotalPages(),
                commentPage.isLast()
        );
    }


    @Override
    public CommentGetResponse getById(Integer id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        return commentMapper.commentToResponse(comment);
    }

    @Override
    public PageResponse<CommentGetResponse> getAllByPost(Integer postId, Pageable pageable) {
        Page<Comment> page = commentRepository.findAllByPost_Id(postId, pageable);
        List<CommentGetResponse> content = page.getContent().stream()
                .map(commentMapper::commentToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }


    @Override
    public PageResponse<CommentGetResponse> getAllByUser(Integer userId, Pageable pageable) {
        Page<Comment> page = commentRepository.findAllByUser_Id(userId, pageable);
        List<CommentGetResponse> content = page.getContent().stream()
                .map(commentMapper::commentToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }


    @Override
    public void update(Integer id, CommentUpdateRequest commentUpdateRequest) {

        commentRepository.findById(id).ifPresent(commentToUpdate -> commentToUpdate.setCommentText(commentUpdateRequest.getCommentText()));
    }

    @Override
    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return commentRepository.existsById(id);
    }
}
