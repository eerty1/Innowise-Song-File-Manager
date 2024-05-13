package com.innowise.exception;

import java.util.NoSuchElementException;

public class EntityNotFoundException extends NoSuchElementException {
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
