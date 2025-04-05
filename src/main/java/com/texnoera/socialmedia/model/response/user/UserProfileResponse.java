package com.texnoera.socialmedia.model.response.user;

import com.texnoera.socialmedia.model.enums.RegistrationStatus;
import com.texnoera.socialmedia.model.response.role.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class UserProfileResponse {

    private Integer id;
    private String username;
    private String email;

    private RegistrationStatus registrationStatus;
    private LocalDateTime lastLogin;

    private String token;
    private String refreshToken;
    private List<RoleResponse> roles;
}
