package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.mapper.PostMapper;
import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import com.texnoera.socialmedia.model.response.user.UserFollowingResponse;
import com.texnoera.socialmedia.repository.PostRepository;
import com.texnoera.socialmedia.service.abstracts.PostService;
import com.texnoera.socialmedia.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;

    @Override
    public List<PostGetResponse> getAll() {
        List<Post> posts = postRepository.findAll();
        return postMapper.postsToGetResponses(posts);
    }

    @Override
    public PostGetResponse getResponseById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new RuntimeException("post not found"));
        return postMapper.postToGetResponse(post);
    }

    @Override
    public Post getById(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new RuntimeException("post not found"));
    }

    @Override
    public List<PostGetResponse> getAllByUser(Long userId) {
        List<Post> userPosts = postRepository.findAllByUser_IdOrderByIdDesc(userId);
        return postMapper.postsToGetResponses(userPosts);
    }

    @Override
    public List<PostGetResponse> getByUserFollowing(Long userId) {
        List<UserFollowingResponse> follows = userService.getUserFollowing(userId);
        List<Post> posts = new ArrayList<>();
        for (UserFollowingResponse user : follows) {
            posts.addAll(postRepository.findAllByUser_IdOrderByIdDesc(user.getUserId()));
        }
        posts.sort(Comparator.comparing(Post::getId).reversed());
        return postMapper.postsToGetResponses(posts);
    }

    @Override
    public Long add(PostAddRequest postAddRequest) {
        Post post = postMapper.postAddRequestToPost(postAddRequest);
        postRepository.save(post);
        return post.getId();
    }

    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
