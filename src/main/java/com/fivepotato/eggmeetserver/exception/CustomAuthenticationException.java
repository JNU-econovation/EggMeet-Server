package com.fivepotato.eggmeetserver.exception;

public class CustomAuthenticationException extends RuntimeException {
    public CustomAuthenticationException(String message) {
        super(ErrorCode.AUTHENTICATION_FAILED.name() + message);
    }
}
