package com.example.ms_order.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    CLIENT_ERROR("Exception from Client"),

    ORDER_NOT_FOUND("Product not found with id: %d"),

    INSUFFICIENT_QUANTITY("Insufficient quantity available. The requested quantity exceeds the available stock. "),

    INSUFFICIENT_BALANCE("Insufficient balance. The requested operation exceeds the available funds in your account.");

    private final String message;
    public String format(Object... args) {
        return String.format(message, args);
    }
}
