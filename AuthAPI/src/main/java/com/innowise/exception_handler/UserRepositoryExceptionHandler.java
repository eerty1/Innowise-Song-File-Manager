package com.innowise.exception_handler;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestControllerAdvice
public class UserRepositoryExceptionHandler {

    private final String NOT_FOUND_MESSAGE = "User not found";

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException() {
        return new ResponseEntity<>(NOT_FOUND_MESSAGE, NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException() {
        return new ResponseEntity<>(NOT_FOUND_MESSAGE, OK);
    }
}
