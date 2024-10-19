package com.example.elotech.exceptions.handlers;

public class DatabaseOperationException extends RuntimeException {

    public DatabaseOperationException(final String message) {
        super(message);
    }
}
