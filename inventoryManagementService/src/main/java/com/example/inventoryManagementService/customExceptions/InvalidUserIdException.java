package com.example.inventoryManagementService.customExceptions;

public class InvalidUserIdException extends Exception {
    public InvalidUserIdException(String message) {
        super(message);
    }
}
