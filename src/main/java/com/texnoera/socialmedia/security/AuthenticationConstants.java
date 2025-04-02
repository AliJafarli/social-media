package com.texnoera.socialmedia.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticationConstants {

    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String USER_EMAIL = "email";
    public static final String USER_REGISTRATION_STATUS = "userRegistrationStatus";
    public static final String LAST_UPDATE = "lastUpdate";
    public static final String ROLE = "roles";
    public static final String ACCESS_KEY_HEADER_NAME = "key";

}
