package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.request.CommentAddRequest;
import com.texnoera.socialmedia.model.request.CommentUpdateRequest;
import com.texnoera.socialmedia.model.response.comment.CommentGetResponse;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;

import java.util.List;

public interface CommentService {

    CommentGetResponse add(CommentAddRequest commentAddRequest);
    List<CommentGetResponse> getAll();
    CommentGetResponse getById(Integer id);
    List<CommentGetResponse> getAllByPost(Integer postId);
    List<CommentGetResponse> getAllByUser(Integer userId);
    void update(Integer id, CommentUpdateRequest commentUpdateRequest);
    void delete(Integer id);
}
