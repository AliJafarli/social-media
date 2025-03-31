package com.texnoera.socialmedia.exception.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.texnoera.socialmedia.exception.constants.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private String userMessage;
    private String errorMessage;
    private List<ValidationErrorResponse> validationErrors;
    @Builder.Default
    @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public ExceptionResponse(ExceptionConstants exceptionConstants, String errorMessage) {
        this.errorMessage = errorMessage;
        this.userMessage = exceptionConstants.getUserMessage();
        timestamp = LocalDateTime.now();
    }
}
