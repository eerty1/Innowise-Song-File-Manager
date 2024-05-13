package com.innowise.exception.exception_handler;

import com.innowise.exception.FailedDeletionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class FailedDeletionExceptionHandler {

    @ExceptionHandler(FailedDeletionException.class)
    public ResponseEntity<String> handleFailedDeletionException(FailedDeletionException failedDeletionException) {
        return new ResponseEntity<>(failedDeletionException.getMessage(), INTERNAL_SERVER_ERROR);
    }
}
