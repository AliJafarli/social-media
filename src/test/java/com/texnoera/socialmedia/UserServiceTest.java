package com.texnoera.socialmedia;

import com.texnoera.socialmedia.mapper.UserMapper;
import com.texnoera.socialmedia.model.entity.Role;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.response.user.UserResponse;
import com.texnoera.socialmedia.repository.RoleRepository;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.service.concretes.UserServiceImpl;
import com.texnoera.socialmedia.service.model.IamServiceUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserServiceTest {

    private UserRepository userRepository;

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    private UserServiceImpl userService;

    private User testUser;
    private UserResponse testUserResponse;
    private Role superAdminRole;

    @BeforeEach
    void setUp() {

        superAdminRole = new Role();
        superAdminRole.setName(IamServiceUserRole.SUPER_ADMIN.getRole());

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

}
