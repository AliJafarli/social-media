package com.texnoera.socialmedia.exception;

import com.texnoera.socialmedia.exception.constants.ExceptionConstants;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

    private final String userMessage;
    private final HttpStatus httpStatus;

    public AppException(ExceptionConstants exceptionConstants, String errorMessage) {
        super(errorMessage);
        this.userMessage = exceptionConstants.getUserMessage();
        this.httpStatus = exceptionConstants.getHttpStatus();
    }

    public AppException(String metadata, ExceptionConstants exceptionConstants) {
        super(exceptionConstants.getUserMessage().concat(" "+metadata));
        this.userMessage = exceptionConstants.getUserMessage();
        this.httpStatus = exceptionConstants.getHttpStatus();
    }

    public AppException(ExceptionConstants exceptionConstants) {
        super(exceptionConstants.getUserMessage());
        this.userMessage = exceptionConstants.getUserMessage();
        this.httpStatus = exceptionConstants.getHttpStatus();
    }

}
