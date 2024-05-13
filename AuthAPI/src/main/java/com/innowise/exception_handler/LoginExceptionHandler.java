package com.innowise.exception_handler;

import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class LoginExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException methodArgumentNotValidException,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request)
    {
        String exceptionMessage = adaptFieldName(getFieldError(methodArgumentNotValidException).getField()) + " " +
                getFieldError(methodArgumentNotValidException).getDefaultMessage();

        return new ResponseEntity<>( exceptionMessage, headers, status);
    }

    private FieldError getFieldError(MethodArgumentNotValidException methodArgumentNotValidException) {
        return methodArgumentNotValidException.getBindingResult().getFieldError();
    }

    private String adaptFieldName(String fieldName) {
        String regexPattern = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";
        return String.join(" ", fieldName.split(regexPattern)).toLowerCase();
    }
}
