package com.ethereal.witch.service.exception;

public class UsernameUniqueViolationExeception extends RuntimeException {
    public UsernameUniqueViolationExeception(String message) {
        super(message);
    }
}
