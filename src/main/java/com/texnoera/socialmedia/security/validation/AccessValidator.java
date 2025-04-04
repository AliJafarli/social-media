package com.texnoera.socialmedia.security.validation;

import com.texnoera.socialmedia.exception.DataExistException;
import com.texnoera.socialmedia.exception.InvalidDataException;
import com.texnoera.socialmedia.exception.InvalidPasswordException;
import com.texnoera.socialmedia.exception.constants.ExceptionConstants;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessValidator {
    private final UserRepository userRepository;


    public void validateNewUser(String username, String email, String password, String confirmPassword) {
        userRepository.findByUsername(username).ifPresent(existingUser -> {
            throw new DataExistException(ExceptionConstants.USERNAME_ALREADY_EXISTS.getMessage(username));
        });

        userRepository.findUserByEmail(email).ifPresent(existingUser -> {
            throw new DataExistException(ExceptionConstants.EMAIL_ALREADY_EXISTS.getMessage(email));
        });

        if (!password.equals(confirmPassword)) {
            throw new InvalidDataException(ExceptionConstants.MISMATCH_PASSWORDS.getMessage());
        }

        if (PasswordUtils.isNotValidPassword(password)) {
            throw new InvalidPasswordException(ExceptionConstants.INVALID_PASSWORD.getMessage());
        }

    }
}
