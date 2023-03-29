package com.hilltop.authservice.exception;

public class InvalidTokenException extends AuthServiceException{
    public InvalidTokenException(String errorMessage) {
        super(errorMessage);
    }
}
