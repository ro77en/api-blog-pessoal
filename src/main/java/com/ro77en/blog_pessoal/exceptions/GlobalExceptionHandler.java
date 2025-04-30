package com.ro77en.blog_pessoal.exceptions;

import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ApiError> handleUsernameExists(EntityExistsException e) {
        var apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Username already exists");
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(apiError);
    }

}
