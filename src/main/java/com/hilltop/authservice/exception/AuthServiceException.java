package com.hilltop.authservice.exception;

public class AuthServiceException extends RuntimeException {
    /**
     * BaseComponentException Exception with error message.
     *
     * @param errorMessage error message
     */
    public AuthServiceException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Authentication Exception with error message and throwable error
     *
     * @param errorMessage error message
     * @param error        error
     */
    public AuthServiceException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
