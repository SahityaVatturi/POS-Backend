package com.example.orderManagementService.customException;

public class OrderAlreadyCanceledException extends Exception {
    public OrderAlreadyCanceledException(String message) {
        super(message);
    }
}
