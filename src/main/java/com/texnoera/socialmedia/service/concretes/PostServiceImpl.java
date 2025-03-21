package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import com.texnoera.socialmedia.service.abstracts.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Override
    public List<PostGetResponse> getAll() {
        return List.of();
    }

    @Override
    public PostGetResponse getResponseById(Long id) {
        return null;
    }

    @Override
    public Post getById(Long id) {
        return null;
    }

    @Override
    public List<PostGetResponse> getAllByUser(Long userId) {
        return List.of();
    }

    @Override
    public List<PostGetResponse> getByUserFollowing(Long userId) {
        return List.of();
    }

    @Override
    public Long add(PostAddRequest postAddRequest) {
        return 0L;
    }

    @Override
    public void delete(Long id) {

    }
}
