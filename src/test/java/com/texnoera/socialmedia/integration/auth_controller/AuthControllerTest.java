package com.texnoera.socialmedia.integration.auth_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.texnoera.socialmedia.SocialMediaApplication;
import com.texnoera.socialmedia.model.request.LoginRequest;
import com.texnoera.socialmedia.model.request.RegistrationUserRequest;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;
import lombok.Setter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(classes = {SocialMediaApplication.class})
@AutoConfigureMockMvc
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@Tag("integration")
class AuthControllerTest {

    @Autowired
    @Setter
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    void loginUserAsSuperAdmin_OK_200() throws Exception {
        LoginRequest request = new LoginRequest("superadmin@gmail.com", "Test11111");

        MvcResult requestResult = mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        IamResponse<?> response = parseResponse(requestResult.getResponse());

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getPayload());
    }

    @Test
    @Order(2)
    void loginUser_INVALID_CREDENTIALS_401() throws Exception {
        LoginRequest request = new LoginRequest("wronguser@example.com", "WrongPassword123!");

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @Order(3)
    @Transactional
    void registerUser_OK_200() throws Exception {
        RegistrationUserRequest request = new RegistrationUserRequest(
                "newuser",
                "newuser@example.com",
                "NewPassword123!",
                "NewPassword123!",
                "firstname",
                "lastname"
        );

        MvcResult requestResult = mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        IamResponse<?> response = parseResponse(requestResult.getResponse());

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getPayload());
    }

    @Test
    @Order(4)
    void registerUser_USER_ALREADY_EXISTS_400() throws Exception {
        RegistrationUserRequest request = new RegistrationUserRequest(
                "user",
                "user@gmail.com",
                "Test1234!",
                "Test1234!",
                "firstname",
                "lastname"
        );

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    private IamResponse<?> parseResponse(MockHttpServletResponse response) throws IOException {
        return objectMapper.readValue(response.getContentAsByteArray(), IamResponse.class);
    }
}

