package com.fivepotato.eggmeetserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomAuthenticationException.class)
    protected ResponseEntity handleCustomAuthenticationException(final CustomAuthenticationException e) {
//        LogView.logErrorStacktraceWithMessage(e);

        return ResponseEntity
                .status(ErrorCode.AUTHENTICATION_FAILED.getStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(CustomTokenException.class)
    protected ResponseEntity handleCustomTokenException(final CustomTokenException e) {
//        LogView.logErrorStacktraceWithMessage(e);

        return ResponseEntity
                .status(ErrorCode.TOKEN_ERROR.getStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(SystemIOException.class)
    protected ResponseEntity handleSystemIOException(final SystemIOException e) {
//      LogView.logErrorStacktraceWithMessage(e);

        return ResponseEntity
                .status(ErrorCode.SYSTEM_IO_ERROR.getStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity handleIllegalArgumentException(final IllegalArgumentException e) {
//        LogView.logErrorStacktraceWithMessage(e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
