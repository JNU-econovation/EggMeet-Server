package com.fivepotato.eggmeetserver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private void printExceptionLog(ErrorCode errorCode, Exception e) {
        log.error(String.format("[%s | %d] : %s", errorCode.toString(), errorCode.getStatus(), e.getMessage()));
    }

    private void printExceptionLog(HttpStatus httpStatus, Exception e) {
        log.error(String.format("[%s | %d] : %s", httpStatus.toString(), httpStatus.value(), e.getMessage()));
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    protected ResponseEntity handleCustomAuthenticationException(final CustomAuthenticationException e) {
        printExceptionLog(ErrorCode.AUTHENTICATION_FAILED, e);

        return ResponseEntity
                .status(ErrorCode.AUTHENTICATION_FAILED.getStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(CustomTokenException.class)
    protected ResponseEntity handleCustomTokenException(final CustomTokenException e) {
        printExceptionLog(ErrorCode.TOKEN_ERROR, e);

        return ResponseEntity
                .status(ErrorCode.TOKEN_ERROR.getStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(SystemIOException.class)
    protected ResponseEntity handleSystemIOException(final SystemIOException e) {
        printExceptionLog(ErrorCode.SYSTEM_IO_ERROR, e);

        return ResponseEntity
                .status(ErrorCode.SYSTEM_IO_ERROR.getStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity handleIllegalArgumentException(final IllegalArgumentException e) {
        printExceptionLog(HttpStatus.BAD_REQUEST, e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(NoContentException.class)
    protected ResponseEntity handleNoContentException(final NoContentException e) {
        printExceptionLog(HttpStatus.NO_CONTENT, e);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(e.getMessage());
    }

    @ExceptionHandler(DuplicatedRequestException.class)
    protected ResponseEntity handleDuplicatedRequestException(final DuplicatedRequestException e) {
        printExceptionLog(HttpStatus.ACCEPTED, e);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(e.getMessage());
    }
}
