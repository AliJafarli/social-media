package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.request.CommentAddRequest;
import com.texnoera.socialmedia.model.request.CommentUpdateRequest;
import com.texnoera.socialmedia.model.response.comment.CommentGetResponse;
import com.texnoera.socialmedia.model.response.page.PageResponse;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    CommentGetResponse add(CommentAddRequest commentAddRequest);
    PageResponse<CommentGetResponse> getAll(Pageable pageable);
    PageResponse<CommentGetResponse> getAllByPost(Integer postId, Pageable pageable);
    PageResponse<CommentGetResponse> getAllByUser(Integer userId, Pageable pageable);

    CommentGetResponse getById(Integer id);
    void update(Integer id, CommentUpdateRequest commentUpdateRequest);
    void delete(Integer id);
}
