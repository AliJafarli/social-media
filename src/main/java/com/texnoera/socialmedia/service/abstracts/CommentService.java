package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.request.CommentAddRequest;
import com.texnoera.socialmedia.model.request.CommentUpdateRequest;
import com.texnoera.socialmedia.model.response.comment.CommentGetResponse;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;

import java.util.List;

public interface CommentService {

    CommentGetResponse add(CommentAddRequest commentAddRequest);
    List<CommentGetResponse> getAll();
    CommentGetResponse getById(Long id);
    List<CommentGetResponse> getAllByPost(Long postId);
    List<CommentGetResponse> getAllByUser(Long userId);
    void update(Long id, CommentUpdateRequest commentUpdateRequest);
    void delete(Long id);
}
