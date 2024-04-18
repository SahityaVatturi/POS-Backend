package com.example.inventoryManagementService.customExceptions;

public class InvalidDataProvidedException extends Exception {
    public InvalidDataProvidedException(String message) {
        super(message);
    }
}
