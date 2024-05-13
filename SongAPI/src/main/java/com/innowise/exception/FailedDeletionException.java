package com.innowise.exception;

public class FailedDeletionException extends RuntimeException {
    public FailedDeletionException() {
    }

    public FailedDeletionException(String message) {
        super(message);
    }
}
