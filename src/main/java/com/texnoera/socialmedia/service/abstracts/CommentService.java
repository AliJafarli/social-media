package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.request.CommentAddRequest;
import com.texnoera.socialmedia.model.request.CommentUpdateRequest;
import com.texnoera.socialmedia.model.response.comment.CommentGetResponse;
import com.texnoera.socialmedia.model.response.page.PageResponse;
import org.springframework.data.domain.Pageable;


public interface CommentService {

    CommentGetResponse add(CommentAddRequest commentAddRequest);
    PageResponse<CommentGetResponse> getAll(Pageable pageable);
    PageResponse<CommentGetResponse> getAllByPost(Integer postId, Pageable pageable);
    PageResponse<CommentGetResponse> getAllByUser(Integer userId, Pageable pageable);

    CommentGetResponse getById(Integer id);
    void update(Integer id, CommentUpdateRequest commentUpdateRequest);

    boolean existsById(Integer id);

    void delete(Integer id);
}
