package com.example.orderManagementService.customException;

public class NotEnoughQuanityException extends Exception {
    public NotEnoughQuanityException(String message) {
        super(message);
    }
}
