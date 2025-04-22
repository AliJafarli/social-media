package com.texnoera.socialmedia.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlreadyFollowingException extends RuntimeException {
    public AlreadyFollowingException(String message) {
        super(message);
    }
}
