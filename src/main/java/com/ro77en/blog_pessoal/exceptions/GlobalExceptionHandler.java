package com.ro77en.blog_pessoal.exceptions;

import jakarta.persistence.EntityExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ApiError> handleUsernameExists(EntityExistsException e) {
        var apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (e.getCause().getMessage().toLowerCase().contains("unique")) {
            status = HttpStatus.CONFLICT;
        }

        var apiError = new ApiError(status, e.getMessage());
        return ResponseEntity.status(status.value()).body(apiError);
    }


}
