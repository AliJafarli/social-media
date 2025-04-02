package com.texnoera.socialmedia.exception.constants;


import com.texnoera.socialmedia.model.constants.ApiConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstants {
    UNEXPECTED_EXCEPTION("Unexpected exception", INTERNAL_SERVER_ERROR),
    METHOD_NOT_ALLOWED_EXCEPTION("Method not allowed exception", METHOD_NOT_ALLOWED),
    NOT_ANNOTATED("Method must be annotated with @Function", INTERNAL_SERVER_ERROR),
    METHOD_ARGUMENT_NOT_VALID("Method argument not valid", BAD_REQUEST),
    NOT_FOUND_EXCEPTION("Not found", NOT_FOUND),
    NOT_EXIST_EXCEPTION("Not exist", BAD_REQUEST),
    HEADER_MISSING_EXCEPTION("Header is missing", BAD_REQUEST),
    METHOD_ARG_TYPE_MISMATCH_EX("Method argument type is not right", BAD_REQUEST),
    BAD_REQUEST_EXCEPTION("Bad request", BAD_REQUEST),
    INVALID_GROUP_ID("Invalid group id", BAD_REQUEST),
    SELECTED_LIMIT_EXCEPTION("Category limit exceed", BAD_REQUEST),


    TOKEN_EXPIRED("Token expired", UNAUTHORIZED),
    UNEXPECTED_ERROR_OCCURRED("Unexpected error occurred", INTERNAL_SERVER_ERROR),
    ERROR_DURING_JWT_PROCESSING("Error during JWT processing", INTERNAL_SERVER_ERROR),
    INVALID_TOKEN_SIGNATURE("Invalid token signature", UNAUTHORIZED),

    AUTHENTICATION_FAILED_FOR_USER("Authentication failed for user: {}", UNAUTHORIZED),
    INVALID_USER_OR_PASSWORD("Invalid email or password. Try again", UNAUTHORIZED),
    INVALID_USER_REGISTRATION_STATUS("Invalid user registration status: %s.", BAD_REQUEST),
    NOT_FOUND_REFRESH_TOKEN("Refresh token not found.", NOT_FOUND),
    MISMATCH_PASSWORDS("Password does not match", BAD_REQUEST),
    INVALID_PASSWORD("Invalid password. It must have: "
            + "length at least " + ApiConstants.REQUIRED_MIN_PASSWORD_LENGTH + ", including "
            + ApiConstants.REQUIRED_MIN_LETTERS_NUMBER_EVERY_CASE_IN_PASSWORD + " letter(s) in upper and lower cases, "
            + ApiConstants.REQUIRED_MIN_CHARACTERS_NUMBER_IN_PASSWORD + " character(s), "
            + ApiConstants.REQUIRED_MIN_DIGITS_NUMBER_IN_PASSWORD + " digit(s).", BAD_REQUEST),
    HAVE_NO_ACCESS("You don't have the necessary permissions", FORBIDDEN)
    ;




    private final String userMessage;
    private final HttpStatus httpStatus;
}
