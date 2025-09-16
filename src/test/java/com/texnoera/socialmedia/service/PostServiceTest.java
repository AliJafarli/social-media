package com.texnoera.socialmedia.service;

import com.texnoera.socialmedia.exception.NotFoundException;
import com.texnoera.socialmedia.mapper.PostMapper;
import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import com.texnoera.socialmedia.repository.PostRepository;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.service.concretes.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postService;

    private Post testPost;
    private PostGetResponse testPostGetResponse;
    private User testUser;

    @BeforeEach
    void setUp(){
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("TestUser");

        testPost = new Post();
        testPost.setId(1);
        testPost.setContent("Test Content");
        testPost.setUser(testUser);

        testPostGetResponse = new PostGetResponse();
        testPostGetResponse.setId(1);
        testPostGetResponse.setContent("Test Content");


    }


    @Test
    void getById_PostExists_ReturnsPostGetResponse(){
        when(postRepository.findWithImagesById(1)).thenReturn(Optional.of(testPost));
        when(postMapper.postToGetResponse(testPost)).thenReturn(testPostGetResponse);

        PostGetResponse result = postService.getResponseById(1);

        assertNotNull(result);
        assertEquals(testPostGetResponse.getId(), result.getId());

        verify(postRepository, times(1)).findWithImagesById(1);
        verify(postMapper, times(1)).postToGetResponse(testPost);
    }

    @Test
    void getByID_PostNotFound_ThrowsException(){
        when(postRepository.findWithImagesById(999)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> postService.getResponseById(999));

        assertTrue(exception.getMessage().contains("not found"));

        verify(postRepository, times(1)).findWithImagesById(999);
        verify(postMapper, never()).postToGetResponse(any(Post.class));
    }

    @Test
    void createPost_OK() {
        PostAddRequest request = new PostAddRequest();
        request.setUserId(1);
        request.setContent("New post content");

        User user = new User();
        user.setId(1);
        user.setUsername("TestUser");

        Post post = new Post();
        post.setContent(request.getContent());
        post.setUser(user);
        post.setCreatedBy("TestUser");

        Post savedPost = new Post();
        savedPost.setId(1);
        savedPost.setContent(request.getContent());
        savedPost.setUser(user);

        PostGetResponse expectedResponse = new PostGetResponse();
        expectedResponse.setId(1);
        expectedResponse.setContent("New post content");

        when(userRepository.findByUsername("TestUser")).thenReturn(Optional.of(user));
        when(postMapper.postAddRequestToPost(request)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(savedPost);
        when(postMapper.postToGetResponse(savedPost)).thenReturn(expectedResponse);


        PostGetResponse result = postService.add(request, "TestUser").getPayload();

        assertNotNull(result);
        assertEquals(expectedResponse.getId(), result.getId());
        assertEquals(expectedResponse.getContent(), result.getContent());

        verify(userRepository, times(1)).findByUsername("TestUser");
        verify(postRepository, times(1)).save(post);
        verify(postMapper, times(1)).postAddRequestToPost(request);
        verify(postMapper, times(1)).postToGetResponse(savedPost);
    }

}
