package com.texnoera.socialmedia.advice;

import com.texnoera.socialmedia.exception.AppException;
import com.texnoera.socialmedia.exception.InvalidPasswordException;
import com.texnoera.socialmedia.exception.response.ExceptionResponse;
import com.texnoera.socialmedia.exception.response.ValidationErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.List;
import java.util.stream.Collectors;

import static com.texnoera.socialmedia.exception.constants.ExceptionConstants.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPasswordException.class)
    public String handleInvalidPasswordException(InvalidPasswordException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(Exception exception, WebRequest request) {
        logError("Exception", exception, request);
        return new ExceptionResponse(UNEXPECTED_EXCEPTION, exception.getMessage());
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ExceptionResponse> handle(AppException exception, WebRequest request) {
        logError("AppException", exception, request);

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .errorMessage(exception.getMessage())
                .userMessage(exception.getUserMessage())
                .build();

        return ResponseEntity.status(exception.getHttpStatus()).body(exceptionResponse);
    }

    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler(MethodNotAllowedException.class)
    public ExceptionResponse handle(MethodNotAllowedException exception, WebRequest request) {
        logError("MethodNotAllowedException", exception, request);
        return new ExceptionResponse(METHOD_NOT_ALLOWED_EXCEPTION, exception.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionResponse handleMethodArgumentNotValid(MethodArgumentTypeMismatchException exception,
                                                          WebRequest request) {
        logError("MethodArgumentTypeMismatchException", exception, request);
        return new ExceptionResponse(METHOD_ARG_TYPE_MISMATCH_EX, exception.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ExceptionResponse handleMethodArgumentNotValid(MissingRequestHeaderException exception,
                                                          WebRequest request) {
        logError("MissingRequestHeaderException", exception, request);
        return new ExceptionResponse(HEADER_MISSING_EXCEPTION, exception.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionResponse handleMethodArgumentNotValid(HttpMessageNotReadableException exception,
                                                          WebRequest request) {
        logError("HttpMessageNotReadableException", exception, request);
        return new ExceptionResponse(BAD_REQUEST_EXCEPTION, exception.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                          WebRequest request) {
        logError("MethodArgumentNotValidException", exception, request);
        List<ValidationErrorResponse> errorsForBadRequest = getErrorsForBadRequest(exception);
        return ExceptionResponse.builder()
                .userMessage("Method arguments not valid")
                .errorMessage(exception.getMessage())
                .validationErrors(errorsForBadRequest)
                .build();
    }

    private List<ValidationErrorResponse> getErrorsForBadRequest(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .map(error -> ValidationErrorResponse.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
    }

    private void logError(String message, Exception exception, WebRequest request) {
        log.error("[{}] Request: {}, Stack Trace: ", message, request.getDescription(false), exception);
    }

}
