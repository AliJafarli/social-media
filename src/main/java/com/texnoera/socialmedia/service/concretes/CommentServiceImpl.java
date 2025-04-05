package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.mapper.CommentMapper;
import com.texnoera.socialmedia.model.entity.Comment;
import com.texnoera.socialmedia.model.request.CommentAddRequest;
import com.texnoera.socialmedia.model.request.CommentUpdateRequest;
import com.texnoera.socialmedia.model.response.comment.CommentGetResponse;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import com.texnoera.socialmedia.repository.CommentRepository;
import com.texnoera.socialmedia.service.abstracts.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<CommentGetResponse> getAll() {
        List<Comment> comments = commentRepository.findAll();
        return commentMapper.commentsToResponses(comments);
    }

    @Override
    public CommentGetResponse getById(Integer id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        return commentMapper.commentToResponse(comment);
    }

    @Override
    public List<CommentGetResponse> getAllByPost(Integer postId) {
        List<Comment> comments = commentRepository.findAllByPost_Id(postId);
        return commentMapper.commentsToResponses(comments);
    }

    @Override
    public List<CommentGetResponse> getAllByUser(Integer userId) {
        List<Comment> comments = commentRepository.findAllByUser_Id(userId);
        return commentMapper.commentsToResponses(comments);
    }

    @Override
    public void update(Integer id, CommentUpdateRequest commentUpdateRequest) {

        commentRepository.findById(id).ifPresent(commentToUpdate -> commentToUpdate.setCommentText(commentUpdateRequest.getCommentText()));
    }

    @Override
    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }
}
