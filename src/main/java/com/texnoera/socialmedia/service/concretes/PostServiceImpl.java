package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.exception.NotFoundException;
import com.texnoera.socialmedia.exception.constants.ExceptionConstants;
import com.texnoera.socialmedia.mapper.PostMapper;
import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.page.PageResponse;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;
import com.texnoera.socialmedia.model.response.user.UserFollowingResponse;
import com.texnoera.socialmedia.repository.PostRepository;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.security.validation.AccessValidator;
import com.texnoera.socialmedia.service.abstracts.PostService;
import com.texnoera.socialmedia.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AccessValidator accessValidator;

    public PageResponse<PostGetResponse> getAll(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);

        List<PostGetResponse> responseList = postMapper.postsToGetResponses(postPage.getContent());

        return new PageResponse<>(
                responseList,
                postPage.getNumber(),
                postPage.getSize(),
                postPage.getTotalElements(),
                postPage.getTotalPages(),
                postPage.isLast()
        );
    }

    @Override
    public PostGetResponse getResponseById(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new RuntimeException("post not found"));
        return postMapper.postToGetResponse(post);
    }

    @Override
    public Post getById(Integer id) {
        return postRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ExceptionConstants.POST_NOT_FOUND_BY_ID.getMessage()));
    }

    @Override
    public PageResponse<PostGetResponse> getAllByUser(Integer userId, Pageable pageable) {
        Page<Post> userPostsPage = postRepository.findAllByUser_Id(userId, pageable);

        List<PostGetResponse> content = userPostsPage.getContent().stream()
                .map(postMapper::postToGetResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                userPostsPage.getNumber(),
                userPostsPage.getSize(),
                userPostsPage.getTotalElements(),
                userPostsPage.getTotalPages(),
                userPostsPage.isLast()
        );
    }


    @Override
    public PageResponse<PostGetResponse> getByUserFollowing(Integer userId, Pageable pageable) {
        List<UserFollowingResponse> follows = userService.getUserFollowing(userId);
        List<Integer> followedUserIds = follows.stream()
                .map(UserFollowingResponse::getUserId)
                .collect(Collectors.toList());

        if (followedUserIds.isEmpty()) {
            return new PageResponse<>(Collections.emptyList(), pageable.getPageNumber(), pageable.getPageSize(), 0, 0, true);
        }

        Page<Post> postsPage = postRepository.findAllByUser_IdIn(followedUserIds, pageable);

        List<PostGetResponse> content = postsPage.getContent().stream()
                .map(postMapper::postToGetResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                content,
                postsPage.getNumber(),
                postsPage.getSize(),
                postsPage.getTotalElements(),
                postsPage.getTotalPages(),
                postsPage.isLast()
        );
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
