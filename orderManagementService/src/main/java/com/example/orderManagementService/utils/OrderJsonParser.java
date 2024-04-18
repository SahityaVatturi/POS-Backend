package com.example.orderManagementService.utils;

import com.example.orderManagementService.customException.InvalidQuantityException;
import com.example.orderManagementService.models.Order;
import com.example.orderManagementService.models.OrderItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderJsonParser {
    public static Order extractOrder(JsonOrder jsonOrder){
        Order order = new Order();
        order.setId(jsonOrder.getId());
        order.setTax(jsonOrder.getTax());
        order.setCreatedTime(new Date());
        order.setSubTotal(jsonOrder.getSubTotal());
        order.setStatus(jsonOrder.getStatus());
        order.setPaymentMethod(jsonOrder.getPaymentMethod());

        return order;
    }

    public static List<OrderItem> extractItems(JsonOrder jsonOrder, Order order) throws InvalidQuantityException {
        List<OrderItem> items = new ArrayList<>();

        for (JsonItem jsonItem : jsonOrder.getJsonItems()){
            OrderItem orderItem  = new OrderItem();
            orderItem.setProduct_id(jsonItem.getId());//product id
            orderItem.setItemId(jsonItem.getItemId());
            orderItem.setName(jsonItem.getName());
            orderItem.setDescription(jsonItem.getDescription());
            orderItem.setPrice(jsonItem.getPrice());
            orderItem.setImageUrl(jsonItem.getImageUrl());


            if(jsonItem.getQuantity() <= 0){
                throw new InvalidQuantityException("Quantity Should be a Positive number");
            }

            orderItem.setQuantity(jsonItem.getQuantity());
            orderItem.setOrder(order);

            items.add(orderItem);
        }
        return items;
    }

    public static List<InventoryProduct> extractInventoryProduct(JsonOrder jsonOrder) throws InvalidQuantityException {
        List<InventoryProduct> inventoryProducts = new ArrayList<>();

        for(JsonItem jsonItem : jsonOrder.getJsonItems()){
            InventoryProduct inventoryProduct = new InventoryProduct();

            inventoryProduct.setId(jsonItem.getId());
            inventoryProduct.setName(jsonItem.getName());
            inventoryProduct.setDescription(jsonItem.getDescription());
            inventoryProduct.setImageUrl(jsonItem.getImageUrl());
            inventoryProduct.setPrice(jsonItem.getPrice());

            if(jsonItem.getQuantity() <= 0){
                throw new InvalidQuantityException("Quantity Should be a Positive number");
            }

            inventoryProduct.setQuantity(jsonItem.getQuantity());

            inventoryProducts.add(inventoryProduct);
        }
        return inventoryProducts;
    }
    public static List<InventoryProduct> extractInventoryProduct(List<OrderItem> items) {
        List<InventoryProduct> inventoryProducts = new ArrayList<>();

        for(OrderItem item : items){
            InventoryProduct inventoryProduct = new InventoryProduct();

            inventoryProduct.setId(item.getProduct_id());
            inventoryProduct.setName(item.getName());
            inventoryProduct.setDescription(item.getDescription());
            inventoryProduct.setImageUrl(item.getImageUrl());
            inventoryProduct.setPrice(item.getPrice());
            inventoryProduct.setQuantity(item.getQuantity());

            inventoryProducts.add(inventoryProduct);
        }
        return inventoryProducts;
    }



//    private float tax;
//    private float subTotal;
//    private String status;
//    private String paymentMethod;

}
