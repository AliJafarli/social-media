package com.texnoera.socialmedia.service;

import com.texnoera.socialmedia.exception.DataExistException;
import com.texnoera.socialmedia.exception.NotFoundException;
import com.texnoera.socialmedia.mapper.UserMapper;
import com.texnoera.socialmedia.model.entity.Role;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.UserAddRequest;
import com.texnoera.socialmedia.model.response.user.UserResponse;
import com.texnoera.socialmedia.repository.RoleRepository;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.security.enums.SocialMediaUserRole;
import com.texnoera.socialmedia.service.concretes.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserResponse testUserResponse;
    private Role superAdminRole;

    @BeforeEach
    void setUp() {

        superAdminRole = new Role();
        superAdminRole.setName(SocialMediaUserRole.SUPER_ADMIN.getRole());

        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("TestUser");
        testUser.setEmail("testuser@gmail.com");
        testUser.setPassword("encodedPassword");
        testUser.setRoles(Set.of(superAdminRole));

        testUserResponse = new UserResponse();
        testUserResponse.setId(1);
        testUserResponse.setUsername("TestUser");
        testUserResponse.setEmail("testuser@gmail.com");
    }

    @Test
    void getById_UserExists_ReturnsUserResponse() {
        when(userRepository.findByIdAndDeletedFalse(1)).thenReturn(Optional.of(testUser));
        when(userMapper.userToResponse(testUser)).thenReturn(testUserResponse);

        UserResponse result = userService.getResponseById(1);

        assertNotNull(result);
        assertEquals(testUserResponse.getId(), result.getId());
        assertEquals(testUserResponse.getUsername(), result.getUsername());

        verify(userRepository, times(1)).findByIdAndDeletedFalse(1);
        verify(userMapper, times(1)).userToResponse(testUser);

    }

    @Test
    void getById_UserNotFound_ThrowsException() {
        when(userRepository.findByIdAndDeletedFalse(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getResponseById(999))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User with ID: 999 was not found"
                );
        verify(userRepository, times(1)).findByIdAndDeletedFalse(999);
        verify(userMapper, times(0)).userToResponse(testUser);
    }

    @Test
    void createUser_AsSuperAdmin_CreatesUserSuccessfully() {
        UserAddRequest request = new UserAddRequest("NewUser", "newuser@gmail.com", "password123!");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(roleRepository.findByName(SocialMediaUserRole.USER.getRole())).thenReturn(Optional.of(superAdminRole));

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setUsername(request.getUsername());
        newUser.setPassword("encodedPassword");
        newUser.setRoles(Collections.singleton(superAdminRole));

        when(userMapper.requestToUser(request)).thenReturn(newUser);

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(userMapper.userToResponse(newUser)).thenReturn(testUserResponse);

        UserResponse result = userService.add(request).getPayload();

        assertNotNull(result);
        assertEquals(testUserResponse.getId(), result.getId());
        assertEquals(testUserResponse.getUsername(), result.getUsername());

        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(userRepository, times(1)).existsByUsername(request.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).userToResponse(newUser);


    }

    @Test
    void createUser_EmailAlreadyExists_ThrowsException() {

        UserAddRequest request = new UserAddRequest("NewUser", "newuser@gmail.com", "password123!");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
        assertThatThrownBy(() -> userService.add(request))
                .isInstanceOf(DataExistException.class)
                .hasMessageContaining("already exists");

        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(userRepository, never()).existsByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}
