package com.example.inventoryManagementService.Authentication;

public class Constants {
    public static final String INVENTORY_BASE_URL = "/inventory";

    public static final String INVENTORY_GET_BY_ID = "/id/{id}";
    public static final String INVENTORY_GET_BY_Name = "/name/{name}";
    public static final String INVENTORY_SEARCH = "/search";
    public static final String INVENTORY_GET_ALL = "/all";

    public static final String INVENTORY_ADD = "/add";
    public static final String INVENTORY_ADD_ALL = "/add/all";

    public static final String INVENTORY_CHECK_STOCK = "/stock/";
    public static final String INVENTORY_CHECK_STOCK_ALL = "/stock/all";

    public static final String INVENTORY_INCREMENT= "/increment";
    public static final String INVENTORY_INCREMENT_ALL = "/increment/all";

    public static final String INVENTORY_REDUCE = "/reduce";
    public static final String INVENTORY_REDUCE_ALL = "/reduce/all";

    public static final String INVENTORY_UPDATE = "/";
    public static final String INVENTORY_DELETE = "/{productId}";


    public static final String KAFKA_TOPIC = "updateInventory";
    public static final String KAFKA_GROUP_ID = "group-1";

    public static final String USER_BASE_URL = "/";
    public static final String USER_GET_BY_ID ="/auth/id/{id}" ;
    public static final String USER_LOGIN = "/login";
    public static final String USER_REGISTER = "/register";




}
