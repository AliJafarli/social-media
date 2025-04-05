package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;

import java.util.List;

public interface PostService {

    List<PostGetResponse> getAll();
    PostGetResponse getResponseById(Integer id);
    Post getById(Integer id);
    List<PostGetResponse> getAllByUser(Integer userId);
    List<PostGetResponse> getByUserFollowing(Integer userId);
    IamResponse<PostGetResponse> add(PostAddRequest postAddRequest, String username);
    void delete(Integer id);
}
