package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.exception.NotFoundException;
import com.texnoera.socialmedia.exception.constants.ExceptionConstants;
import com.texnoera.socialmedia.mapper.PostMapper;
import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;
import com.texnoera.socialmedia.model.response.user.UserFollowingResponse;
import com.texnoera.socialmedia.repository.PostRepository;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.security.validation.AccessValidator;
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
    private final UserRepository userRepository;
    private final AccessValidator accessValidator;

    @Override
    public List<PostGetResponse> getAll() {
        List<Post> posts = postRepository.findAll();
        return postMapper.postsToGetResponses(posts);
    }

    @Override
    public PostGetResponse getResponseById(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new RuntimeException("post not found"));
        return postMapper.postToGetResponse(post);
    }

    @Override
    public Post getById(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ExceptionConstants.POST_NOT_FOUND_BY_ID.getMessage()));

        return post;
    }

    @Override
    public List<PostGetResponse> getAllByUser(Integer userId) {
        List<Post> userPosts = postRepository.findAllByUser_IdOrderByIdDesc(userId);
        return postMapper.postsToGetResponses(userPosts);
    }

    @Override
    public List<PostGetResponse> getByUserFollowing(Integer userId) {
        List<UserFollowingResponse> follows = userService.getUserFollowing(userId);
        List<Post> posts = new ArrayList<>();
        for (UserFollowingResponse user : follows) {
            posts.addAll(postRepository.findAllByUser_IdOrderByIdDesc(user.getUserId()));
        }
        posts.sort(Comparator.comparing(Post::getId).reversed());
        return postMapper.postsToGetResponses(posts);
    }

    @Override
    public IamResponse<PostGetResponse> add(PostAddRequest postAddRequest, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException(ExceptionConstants.USERNAME_NOT_FOUND.getMessage(username)));

        Post post = postMapper.postAddRequestToPost(postAddRequest);
        post.setUser(user);
        post.setCreatedBy(username);
        Post savedPost = postRepository.save(post);
        PostGetResponse postGetResponse = postMapper.postToGetResponse(savedPost);
        return IamResponse.createSuccessful(postGetResponse);
    }

    @Override
    public void delete(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ExceptionConstants.POST_NOT_FOUND_BY_ID.getMessage()));
        accessValidator.validateAdminOrOwnerAccess(post.getUser().getId());
        postRepository.deleteById(id);
    }
}
