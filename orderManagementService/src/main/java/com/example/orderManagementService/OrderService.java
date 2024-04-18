package com.example.orderManagementService;

import com.example.orderManagementService.customException.*;
import com.example.orderManagementService.models.Order;
import com.example.orderManagementService.utils.JsonOrder;
import com.example.orderManagementService.utils.InventoryProduct;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    Order getById(int orderId) throws OrderNotFoundException;

    Page<Order> getAll(int page, int size);

    Order postOrder(JsonOrder order) throws InvalidQuantityException, NotEnoughQuanityException;

    List<Order> postOrder(List<JsonOrder> order) throws InvalidQuantityException, NotEnoughQuanityException;

    void updateInventoryViaKafka(List<InventoryProduct> orderItems) throws JsonProcessingException;

    Order cancelOrder(int orderId) throws JsonProcessingException, OrderAlreadyCanceledException, OrderNotFoundException;

}
