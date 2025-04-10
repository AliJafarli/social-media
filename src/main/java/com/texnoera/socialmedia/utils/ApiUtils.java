package com.texnoera.socialmedia.utils;

import com.texnoera.socialmedia.model.constants.ApiConstants;
import com.texnoera.socialmedia.security.JwtTokenProvider;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ApiUtils {
    private final JwtTokenProvider jwtTokenProvider;

    public static String getMethodName() {
        try {
            return new Throwable().getStackTrace()[1].getMethodName();
        } catch (Exception cause) {
            return ApiConstants.UNDEFINED;
        }
    }

//    public static Cookie createAuthCookie(String value) {
//        Cookie authorizationCookie = new Cookie(HttpHeaders.AUTHORIZATION, value);
//        authorizationCookie.setHttpOnly(true);
//        authorizationCookie.setSecure(true);
//        authorizationCookie.setPath("/");
//        authorizationCookie.setMaxAge(300);
//        return authorizationCookie;
//    }

    public static String generateUuidWithoutDash(){
        return UUID.randomUUID().toString().replace(ApiConstants.DASH, "");
    }

    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Integer getUserIdFromAuthentication() {
        String jwtToken = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        return Integer.parseInt(jwtTokenProvider.getUserId(jwtToken));
    }
}
