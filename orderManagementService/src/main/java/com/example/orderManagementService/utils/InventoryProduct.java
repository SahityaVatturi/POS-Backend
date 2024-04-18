package com.example.orderManagementService.utils;


import lombok.Data;

@Data
public class InventoryProduct {
    private int id;
    private String name;
    private String description;
    private float price;
    private String imageUrl;
    private int quantity;
}
