package com.ethereal.witch.service.exception;

public class UnnauthorizedException extends RuntimeException{
    public UnnauthorizedException(String message){
        super(message);
    }
}
