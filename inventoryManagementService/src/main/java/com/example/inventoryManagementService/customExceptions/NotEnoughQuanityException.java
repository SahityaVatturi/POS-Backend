package com.example.inventoryManagementService.customExceptions;

public class NotEnoughQuanityException extends Exception {
    public NotEnoughQuanityException(String message) {
        super(message);
    }
}
