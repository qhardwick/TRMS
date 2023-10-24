package com.infy.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Environment environment;

    @Autowired
    public GlobalExceptionHandler(Environment environment) {
        this.environment = environment;
    }

    // Handles Exceptions thrown from Validation failures as defined in the DTOs:
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Flux<ResponseEntity<ErrorMessage>> handleValidationExceptions(MethodArgumentNotValidException e) {
        return Flux.fromIterable(e.getBindingResult().getFieldErrors())
                .map(fieldError -> new ErrorMessage(HttpStatus.BAD_REQUEST.value(), fieldError.getDefaultMessage()))
                .map(errorMessage -> ResponseEntity.badRequest().body(errorMessage));
    }
}