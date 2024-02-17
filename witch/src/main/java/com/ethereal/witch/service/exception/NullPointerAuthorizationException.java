package com.ethereal.witch.service.exception;

public class NullPointerAuthorizationException extends RuntimeException{
    public NullPointerAuthorizationException(String message){
        super(message);
    }
}
