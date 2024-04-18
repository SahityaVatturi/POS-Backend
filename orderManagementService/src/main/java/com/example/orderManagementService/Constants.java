package com.example.orderManagementService;

public class Constants {
    public static final String ORDER_BASE_URL = "/order";
    public static final String ORDER_GET_ALL = "/all";
    public static final String ORDER_GET_BY_ID ="/id/{id}";
    public static final String ORDER_ADD = "/add";
    public static final String ORDER_ADD_ALL = "/add/all";
    public static final String ORDER_CANCEL = "/cancel/{orderId}";

    public static final String TOPIC="updateInventory";
    public static final String CANCELED = "canceled";
}
