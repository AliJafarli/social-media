package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.page.PageResponse;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;
import org.springframework.data.domain.Pageable;


public interface PostService {

    PageResponse<PostGetResponse> getAll(Pageable pageable);

    PostGetResponse getResponseById(Integer id);
    Post getById(Integer id);
    PageResponse<PostGetResponse> getAllByUser(Integer userId, Pageable pageable);
    PageResponse<PostGetResponse> getByUserFollowing(Integer userId, Pageable pageable);
    IamResponse<PostGetResponse> add(PostAddRequest postAddRequest, String username);
    void delete(Integer id);
}
