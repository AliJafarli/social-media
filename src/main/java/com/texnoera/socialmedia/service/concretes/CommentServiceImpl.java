package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.model.request.CommentAddRequest;
import com.texnoera.socialmedia.model.request.CommentUpdateRequest;
import com.texnoera.socialmedia.model.response.comment.CommentGetResponse;
import com.texnoera.socialmedia.service.abstracts.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public void add(CommentAddRequest commentAddRequest) {

    }

    @Override
    public List<CommentGetResponse> getAll() {
        return List.of();
    }

    @Override
    public CommentGetResponse getById(Long id) {
        return null;
    }

    @Override
    public List<CommentGetResponse> getAllByPost(Long postId) {
        return List.of();
    }

    @Override
    public List<CommentGetResponse> getAllByUser(Long userId) {
        return List.of();
    }

    @Override
    public void update(Long id, CommentUpdateRequest commentUpdateRequest) {

    }

    @Override
    public void delete(Long id) {

    }
}
