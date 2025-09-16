package com.texnoera.socialmedia.integration.user_controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.texnoera.socialmedia.SocialMediaApplication;
import com.texnoera.socialmedia.exception.InvalidDataException;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.UserAddRequest;
import com.texnoera.socialmedia.model.request.UserUpdateRequest;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;
import com.texnoera.socialmedia.model.response.user.UserResponse;
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
@SpringBootTest(classes = SocialMediaApplication.class)
@AutoConfigureMockMvc
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@Tag("integration")
class UserControllerTest {

    @Autowired
    @Setter
    private MockMvc mockMvc;

    @Autowired @Setter
    private JwtTokenProvider jwtTokenProvider;

    @Autowired @Setter
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String adminJwt;
    private String userJwt;

    @BeforeAll
    @Transactional
    void authorize() {
        // Admin authorization (ID: 1)
        User admin = userRepository.findById(1)
                .orElseThrow(() -> new InvalidDataException("Admin with ID: 1 not found"));
        Hibernate.initialize(admin.getRoles());
        this.adminJwt = "Bearer " + jwtTokenProvider.generateToken(admin);

        // Regular user authorization (ID: 3)
        User user = userRepository.findById(3)
                .orElseThrow(() -> new InvalidDataException("User with ID: 3 not found"));
        Hibernate.initialize(user.getRoles());
        this.userJwt = jwtTokenProvider.generateToken(user);
    }

    @Test
    void getAllUsers_200_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .header(HttpHeaders.AUTHORIZATION, adminJwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllUsers_Unauthorized_401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @Transactional
    void createUser_AsAdmin_OK_200() throws Exception {
        UserAddRequest request = new UserAddRequest(
                "newUser",
                "password123",
                "newuser@gmail.com");

        MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users/create")
                        .header(HttpHeaders.AUTHORIZATION, adminJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        IamResponse<UserResponse> response = parseUserDTOResponse(requestResult.getResponse().getContentAsByteArray());

        UserResponse resultBody = Objects.nonNull(response.getPayload()) ? response.getPayload() : null;
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(resultBody);
        Assertions.assertEquals(request.getEmail(), resultBody.getEmail());
        Assertions.assertEquals(request.getUsername(), resultBody.getUsername());
    }

    @Test
    @Transactional
    void createUser_asUser_Unauthorized_401() throws Exception {
        UserAddRequest request = new UserAddRequest(
                "newUser",
                "password123",
                "newuser@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users/create")
                        .header(HttpHeaders.AUTHORIZATION, userJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @Transactional
    void updateUser_AsAdmin_OK_200() throws Exception {
        UserUpdateRequest request = new UserUpdateRequest("updates_username", "updatedUser@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/users/1")
                        .header(HttpHeaders.AUTHORIZATION, adminJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    void deleteUser_AsAdmin_200_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/1")
                        .header(HttpHeaders.AUTHORIZATION, adminJwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    void deleteUser_AsUser_Unauthorized_401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/1")
                        .header(HttpHeaders.AUTHORIZATION, userJwt)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    private IamResponse<UserResponse> parseUserDTOResponse(byte[] contentAsByteArray) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(contentAsByteArray, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

