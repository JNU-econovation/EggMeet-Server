package com.fivepotato.eggmeetserver.exception;

public class DuplicatedRequestException extends RuntimeException {
    public DuplicatedRequestException(String message) {
        super(message);
    }
}
