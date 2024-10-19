package com.example.elotech.exceptions.handlers;

public class DataAlreadyInUseException extends RuntimeException {
    public DataAlreadyInUseException(final String message) {
        super(message);
    }
}
