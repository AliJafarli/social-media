package com.texnoera.socialmedia.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialMediaUserRole {
    USER("USER"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN");

    private final String role;

    public static SocialMediaUserRole fromName(String name) {
        return SocialMediaUserRole.valueOf(name.toUpperCase());
    }

}
