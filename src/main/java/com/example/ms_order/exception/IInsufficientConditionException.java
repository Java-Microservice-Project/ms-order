package com.example.ms_order.exception;

public class InsufficientConditionException extends RuntimeException{
    public InsufficientConditionException(String message) {
        super(message);
    }
}
