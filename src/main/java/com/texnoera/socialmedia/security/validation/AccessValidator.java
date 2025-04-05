package com.texnoera.socialmedia.security.validation;

import com.texnoera.socialmedia.exception.DataExistException;
import com.texnoera.socialmedia.exception.InvalidDataException;
import com.texnoera.socialmedia.exception.InvalidPasswordException;
import com.texnoera.socialmedia.exception.NotFoundException;
import com.texnoera.socialmedia.exception.constants.ExceptionConstants;
import com.texnoera.socialmedia.model.entity.User;
import com.texnoera.socialmedia.repository.UserRepository;
import com.texnoera.socialmedia.service.model.IamServiceUserRole;
import com.texnoera.socialmedia.utils.ApiUtils;
import com.texnoera.socialmedia.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class AccessValidator {
    private final UserRepository userRepository;
    private final ApiUtils apiUtils;

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

    public boolean isAdminOrSuperAdmin(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ExceptionConstants.USER_NOT_FOUND_BY_ID.getMessage(userId)));

        return user.getRoles().stream()
                .map(role -> IamServiceUserRole.fromName(role.getName()))
                .anyMatch(role -> role == IamServiceUserRole.ADMIN || role == IamServiceUserRole.SUPER_ADMIN);
    }

    @SneakyThrows
    public void validateAdminOrOwnerAccess(Integer ownerId) {
        Integer currentUserId = apiUtils.getUserIdFromAuthentication();

        if (!currentUserId.equals(ownerId) && !isAdminOrSuperAdmin(currentUserId)) {
            throw new AccessDeniedException(ExceptionConstants.HAVE_NO_ACCESS.getMessage());
        }
    }

}
