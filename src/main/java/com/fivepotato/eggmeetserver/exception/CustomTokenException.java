package com.fivepotato.eggmeetserver.exception;

public class CustomTokenException extends RuntimeException {
    public CustomTokenException(String message) {
        super(ErrorCode.TOKEN_ERROR.name() + message);
    }
}
