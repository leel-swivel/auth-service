package com.hilltop.authservice.exception;

public class InvalidAccessException extends AuthServiceException{
    public InvalidAccessException(String errorMessage) {
        super(errorMessage);
    }
}
