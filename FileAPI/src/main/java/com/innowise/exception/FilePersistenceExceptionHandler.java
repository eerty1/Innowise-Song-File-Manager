package com.innowise.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class FilePersistenceExceptionHandler {
    @Value("${exception-message.io-exception}")
    private String exceptionMessage;

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException() {
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
