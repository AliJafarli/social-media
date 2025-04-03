package com.texnoera.socialmedia.service.concretes;

import com.texnoera.socialmedia.exception.InvalidDataException;
import com.texnoera.socialmedia.exception.constants.ExceptionConstants;
import com.texnoera.socialmedia.mapper.UserMapper;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.model.request.LoginRequest;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;
import com.texnoera.socialmedia.model.response.user.UserProfileResponse;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.security.JwtTokenProvider;
import com.texnoera.socialmedia.service.abstracts.AuthService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;


    @Override
    public IamResponse<UserProfileResponse> login(@NotNull LoginRequest request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e){
            throw new InvalidDataException(ExceptionConstants.INVALID_USER_OR_PASSWORD.getUserMessage());
        }
User user = userRepository.findUserByEmailAndDeletedFalse(request.getEmail())
        .orElseThrow(() -> new InvalidDataException(ExceptionConstants.INVALID_USER_OR_PASSWORD.getUserMessage()));

        String token = jwtTokenProvider.generateToken(user);
        UserProfileResponse userProfileResponse = userMapper.toUserProfileResponse(user,token);
        userProfileResponse.setToken(token);

        return IamResponse.createSuccessfulWithNewToken(userProfileResponse);
    }
}
