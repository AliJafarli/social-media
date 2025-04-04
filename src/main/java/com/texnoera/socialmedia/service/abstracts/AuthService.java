package com.texnoera.socialmedia.service.abstracts;

import com.texnoera.socialmedia.model.request.LoginRequest;
import com.texnoera.socialmedia.model.response.someResponses.IamResponse;
import com.texnoera.socialmedia.model.response.user.UserProfileResponse;

public interface AuthService {

    IamResponse<UserProfileResponse> login(LoginRequest request);

    IamResponse<UserProfileResponse> refreshAccessToken(String refreshToken);

//    IamResponse<UserProfileResponse> registerUser(RegistrationUserRequest request);

}