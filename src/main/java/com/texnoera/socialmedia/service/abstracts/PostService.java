package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;

import java.util.List;

public interface PostService {

    List<PostGetResponse> getAll();
    PostGetResponse getResponseById(Long id);
    Post getById(Long id);
    List<PostGetResponse> getAllByUser(Long userId);
    List<PostGetResponse> getByUserFollowing(Long userId);
    Long add(PostAddRequest postAddRequest);
    void delete(Long id);
}
