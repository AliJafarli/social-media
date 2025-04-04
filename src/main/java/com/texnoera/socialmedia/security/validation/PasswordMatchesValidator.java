package com.texnoera.socialmedia.security.validation;

import com.texnoera.socialmedia.model.request.RegistrationUserRequest;
import com.texnoera.socialmedia.utils.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegistrationUserRequest> {

    @Override
    public boolean isValid(RegistrationUserRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return request.getPassword().equals(request.getConfirmPassword());
    }

}
