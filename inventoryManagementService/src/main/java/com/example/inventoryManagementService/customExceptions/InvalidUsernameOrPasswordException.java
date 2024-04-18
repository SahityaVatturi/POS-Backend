package com.example.inventoryManagementService.customExceptions;

public class InvalidUsernameOrPasswordException extends Exception {
    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }
}
