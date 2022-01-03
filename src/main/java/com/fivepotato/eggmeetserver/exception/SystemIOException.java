package com.fivepotato.eggmeetserver.exception;

public class SystemIOException extends RuntimeException {
    public SystemIOException() {
        super(ErrorCode.SYSTEM_IO_ERROR.name() + " : "+ ErrorCode.SYSTEM_IO_ERROR_MESSAGE);
    }
}
