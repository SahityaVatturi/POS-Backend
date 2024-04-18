package com.example.orderManagementService.utils;

import lombok.Data;

import java.util.List;

@Data
public class JsonOrder {
    private int id;
    private float tax;
    private float subTotal;
    private String status;
    private String paymentMethod;
    private List<JsonItem> jsonItems;
}


