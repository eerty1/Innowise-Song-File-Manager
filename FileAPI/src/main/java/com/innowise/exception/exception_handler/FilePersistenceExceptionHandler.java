package com.innowise.exception.exception_handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.core.exception.SdkClientException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class FilePersistenceExceptionHandler {

    @ExceptionHandler({IOException.class, SdkClientException.class})
    public ResponseEntity<String> handleFilePersistenceException() {
        return new ResponseEntity<>("File wasn't saved", INTERNAL_SERVER_ERROR);
    }
}
