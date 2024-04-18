package com.example.orderManagementService.customException;

public class PostgresException extends Exception {
    public PostgresException(String message) {
        super(message);
    }
}
