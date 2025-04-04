package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.exception.DataExistException;
import com.texnoera.socialmedia.exception.InvalidDataException;
import com.texnoera.socialmedia.exception.NotFoundException;
import com.texnoera.socialmedia.exception.constants.ExceptionConstants;
import com.texnoera.socialmedia.mapper.UserMapper;
import com.texnoera.socialmedia.model.entity.RefreshToken;
import com.texnoera.socialmedia.model.entity.Role;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.LoginRequest;
import com.texnoera.socialmedia.model.request.RegistrationUserRequest;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;
import com.texnoera.socialmedia.model.response.user.UserProfileResponse;
import com.texnoera.socialmedia.repository.RoleRepository;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.security.JwtTokenProvider;
import com.texnoera.socialmedia.service.abstracts.AuthService;
import com.texnoera.socialmedia.service.abstracts.RefreshTokenService;
import com.texnoera.socialmedia.service.model.IamServiceUserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public IamResponse<UserProfileResponse> login(@NotNull LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidDataException(ExceptionConstants.INVALID_USER_OR_PASSWORD.getUserMessage());
        }
        User user = userRepository.findUserByEmailAndDeletedFalse(request.getEmail())
                .orElseThrow(() -> new InvalidDataException(ExceptionConstants.INVALID_USER_OR_PASSWORD.getUserMessage()));

        RefreshToken refreshToken = refreshTokenService.generateOrUpdateRefreshToken(user);
        String token = jwtTokenProvider.generateToken(user);
        UserProfileResponse userProfileResponse = userMapper.toUserProfileResponse(user, token, refreshToken.getToken());
        userProfileResponse.setToken(token);

        return IamResponse.createSuccessfulWithNewToken(userProfileResponse);
    }

    @Override
    public IamResponse<UserProfileResponse> refreshAccessToken(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenService.validateAndRefreshToken(refreshTokenValue);
        User user = refreshToken.getUser();

        String accessToken = jwtTokenProvider.generateToken(user);

        return IamResponse.createSuccessfulWithNewToken(
                userMapper.toUserProfileResponse(user, accessToken, refreshToken.getToken()));
    }

    @Override
    public IamResponse<UserProfileResponse> registerUser(@NotNull RegistrationUserRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(existingUser -> {
            throw new DataExistException(ExceptionConstants.USERNAME_ALREADY_EXISTS.getMessage(request.getUsername()));
        });

        userRepository.findUserByEmail(request.getEmail()).ifPresent(existingEmail -> {
            throw new DataExistException(ExceptionConstants.EMAIL_ALREADY_EXISTS.getMessage(request.getEmail()));
        });

        Role userRole = roleRepository.findByName(IamServiceUserRole.USER.getRole())
                .orElseThrow(()->new NotFoundException(ExceptionConstants.USER_ROLE_NOT_FOUND.getMessage()));

        User newUser = userMapper.fromDto(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        newUser.setRoles(roles);
        userRepository.save(newUser);

        RefreshToken refreshToken  = refreshTokenService.generateOrUpdateRefreshToken(newUser);
        String token = jwtTokenProvider.generateToken(newUser);
        UserProfileResponse userProfileResponse = userMapper.toUserProfileResponse(newUser, token, refreshToken.getToken());
        userProfileResponse.setToken(token);

        return IamResponse.createSuccessfulWithNewToken(userProfileResponse);
    }
}
