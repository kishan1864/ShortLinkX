package com.shortlinkx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>>
    handleUserAlreadyExists(
            UserAlreadyExistsException exception
    ) {

        return buildResponse(
                HttpStatus.CONFLICT,
                "USER_ALREADY_EXISTS",
                exception.getMessage()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>>
    handleBadCredentials(
            BadCredentialsException exception
    ) {

        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                "INVALID_CREDENTIALS",
                "Invalid email or password"
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>>
    handleValidation(
            MethodArgumentNotValidException exception
    ) {

        Map<String, String> errors =
                new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        Map<String, Object> body =
                new LinkedHashMap<>();

        body.put("status", 400);
        body.put("error", "VALIDATION_FAILED");
        body.put("messages", errors);

        return ResponseEntity
                .badRequest()
                .body(body);
    }

    private ResponseEntity<Map<String, Object>>
    buildResponse(
            HttpStatus status,
            String error,
            String message
    ) {

        Map<String, Object> body =
                new LinkedHashMap<>();

        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);

        return ResponseEntity
                .status(status)
                .body(body);
    }
}