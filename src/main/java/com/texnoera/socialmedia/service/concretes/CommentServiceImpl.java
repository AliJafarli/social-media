package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.mapper.CommentMapper;
import com.texnoera.socialmedia.model.entity.Comment;
import com.texnoera.socialmedia.model.request.CommentAddRequest;
import com.texnoera.socialmedia.model.request.CommentUpdateRequest;
import com.texnoera.socialmedia.model.response.comment.CommentGetResponse;
import com.texnoera.socialmedia.model.response.page.PageResponse;
import com.texnoera.socialmedia.repository.CommentRepository;
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

    @Override
    public CommentGetResponse add(CommentAddRequest commentAddRequest) {
        Comment comment = commentMapper.addRequestToComment(commentAddRequest);
        commentRepository.save(comment);
        return commentMapper.commentToResponse(comment);
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
