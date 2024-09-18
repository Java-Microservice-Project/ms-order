package com.example.ms_order.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomFeignException.class)
    public ResponseEntity<ErrorResponse> handle(HttpServletRequest request,
                                                CustomFeignException exception) {
        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.builder()
                .message(exception.getMessage())
                .status(BAD_REQUEST.value())
                .path(request.getServletPath())
                .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(NotFoundException ex, HttpServletRequest request) {
        log.error("NotFoundException : " + ex);
        return ResponseEntity.status(NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .status(NOT_FOUND.value())
                        .error(NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(InsufficientConditionException.class)
    public ResponseEntity<ExceptionResponse> handle(InsufficientConditionException ex, HttpServletRequest request) {
        log.error("InsufficientConditionException : " + ex);
        return ResponseEntity.status(NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .status(NOT_FOUND.value())
                        .error(NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handle(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("MethodArgumentNotValidException : " + ex);

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return ResponseEntity.status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .status(BAD_REQUEST.value())
                        .error(BAD_REQUEST.getReasonPhrase())
                        .message("Validation failed")
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .validationErrors(errors)
                        .build());
    }
}
