package com.texnoera.socialmedia.service;

import com.texnoera.socialmedia.exception.NotFoundException;
import com.texnoera.socialmedia.mapper.CommentMapper;
import com.texnoera.socialmedia.model.entity.Comment;
import com.texnoera.socialmedia.model.entity.Post;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.CommentAddRequest;
import com.texnoera.socialmedia.model.response.comment.CommentGetResponse;
import com.texnoera.socialmedia.repository.CommentRepository;
import com.texnoera.socialmedia.repository.PostRepository;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.service.concretes.CommentServiceImpl;
import com.texnoera.socialmedia.utils.ApiUtils;
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
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Comment testComment;
    private CommentGetResponse testCommentGetResponse;
    private User testUser;
    private Post testPost;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("TestUser");

        testPost = new Post();
        testPost.setId(1);

        testComment = new Comment();
        testComment.setId(1);
        testComment.setCommentText("Test Comment");
        testComment.setPost(testPost);
        testComment.setUser(testUser);

        testCommentGetResponse = new CommentGetResponse();
        testCommentGetResponse.setId(1);
        testCommentGetResponse.setCommentText("Test Comment");

    }

    @Test
    void getCommentBiId_CommentExists_ReturnsCommentDTO(){
        when(commentRepository.findById(1)).thenReturn(Optional.of(testComment));
        when(commentMapper.commentToResponse(testComment)).thenReturn(testCommentGetResponse);

        CommentGetResponse result = commentService.getById(1);

        assertNotNull(result);
        assertEquals(testCommentGetResponse.getId(), result.getId());
        assertEquals(testCommentGetResponse.getCommentText(), result.getCommentText());

        verify(commentRepository, times(1)).findById(1);
        verify(commentMapper, times(1)).commentToResponse(testComment);

    }

    @Test
    void getCommentById_CommentNotFound_ThrowsException(){
        when(commentRepository.findById(999)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> commentService.getById(999));

        assertTrue(exception.getMessage().contains("not found"));

        verify(commentRepository, times(1)).findById(999);
        verify(commentMapper, never()).commentToResponse(any(Comment.class));
    }

    @Test
    void createComment_OK() {
        CommentAddRequest request = new CommentAddRequest(testPost.getId(), testUser.getId(), "New comment");

        User user = testUser;
        Post post = testPost;

        Comment commentToSave = new Comment();
        commentToSave.setCommentText(request.getCommentText());
        commentToSave.setUser(user);
        commentToSave.setPost(post);

        Comment savedComment = new Comment();
        savedComment.setId(100);
        savedComment.setCommentText(request.getCommentText());
        savedComment.setUser(user);
        savedComment.setPost(post);

        CommentGetResponse response = new CommentGetResponse();
        response.setId(100);
        response.setUserId(user.getId());
        response.setPostId(post.getId());
        response.setUsername(user.getUsername());
        response.setCommentText(request.getCommentText());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> {
            Comment c = invocation.getArgument(0);
            c.setId(100);
            return c;
        });
        when(commentRepository.findById(100)).thenReturn(Optional.of(savedComment));
        when(commentMapper.commentToResponse(savedComment)).thenReturn(response);

        CommentGetResponse result = commentService.add(request);

        assertNotNull(result);
        assertEquals(response.getId(), result.getId());
        assertEquals(response.getCommentText(), result.getCommentText());

        verify(userRepository).findById(user.getId());
        verify(postRepository).findById(post.getId());
        verify(commentRepository).save(any(Comment.class));
        verify(commentRepository).findById(100);
        verify(commentMapper).commentToResponse(savedComment);
    }

}
