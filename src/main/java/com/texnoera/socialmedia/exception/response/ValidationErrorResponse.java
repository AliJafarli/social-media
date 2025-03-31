package com.texnoera.socialmedia.exception.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ValidationErrorResponse {
    private String field;
    private String message;
}
