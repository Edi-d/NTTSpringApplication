package org.example.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.NTTAppBaseException;
import org.example.exception.NTTInternalServerException;
import org.example.exception.OwnerInvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.example.domain.dto.error.ErrorDto;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OwnerInvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleException(OwnerInvalidCredentialsException e) {
        log.error("InvalidCredentialsException: {}", e.getMessage());
        return ErrorDto.builder()
                .message(e.getMessage())
                .build();
    }
    @ExceptionHandler(NTTInternalServerException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleException(NTTInternalServerException e) {
        log.error("NTTApp Internal Server Error: {}", e.getMessage());
        return ErrorDto.builder()
                .message(e.getMessage())
                .build();
    }
    @ExceptionHandler(NTTAppBaseException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleException(NTTAppBaseException e) {
        log.error("App Base Exception: {}", e.getMessage());
        return ErrorDto.builder()
                .message(e.getMessage())
                .build();
    }
}
