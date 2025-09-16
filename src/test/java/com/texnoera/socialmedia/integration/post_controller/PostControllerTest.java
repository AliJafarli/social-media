package com.texnoera.socialmedia.integration.post_controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.texnoera.socialmedia.SocialMediaApplication;
import com.texnoera.socialmedia.exception.InvalidDataException;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.PostAddRequest;
import com.texnoera.socialmedia.model.response.post.PostGetResponse;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.security.JwtTokenProvider;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(classes = {SocialMediaApplication.class})
@AutoConfigureMockMvc
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@Tag("integration")
class PostControllerTest {

    @Autowired
    @Setter
    private MockMvc mockMvc;

    @Autowired @Setter
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String currentJwt;

    @BeforeAll
    @Transactional
    void authorize() {
        User user = userRepository.findById(1)
                .orElseThrow(() -> new InvalidDataException("User with ID: 1 not found"));

        Hibernate.initialize(user.getRoles());
        this.currentJwt = "Bearer " + jwtTokenProvider.generateToken(user);
    }

    @Test
    void getPosts_OK_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/posts")
                        .header(HttpHeaders.AUTHORIZATION, currentJwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    void createPost_OK_200() throws Exception {
        PostAddRequest request = new PostAddRequest(50, "Simple content");

        MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/posts/create")
                        .header(HttpHeaders.AUTHORIZATION, currentJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        IamResponse<PostGetResponse> response = parsePostDTOResponse(requestResult.getResponse().getContentAsByteArray());

        PostGetResponse resultBody = Objects.nonNull(response.getPayload()) ? response.getPayload() : null;
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(resultBody);
        Assertions.assertEquals(request.getContent(), resultBody.getContent());

    }


    @Test
    @Transactional
    void deletePost_OK_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/posts/1")
                        .header(HttpHeaders.AUTHORIZATION, currentJwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private IamResponse<PostGetResponse> parsePostDTOResponse(byte[] contentAsByteArray) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(IamResponse.class, PostGetResponse.class);
            return objectMapper.readValue(contentAsByteArray, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

