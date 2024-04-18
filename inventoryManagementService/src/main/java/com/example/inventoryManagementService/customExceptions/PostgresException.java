package com.example.inventoryManagementService.customExceptions;

public class PostgresException extends Exception {
    public PostgresException(String message) {
        super(message);
    }
}
