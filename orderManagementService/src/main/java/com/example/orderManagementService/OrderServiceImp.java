package com.example.orderManagementService;

import com.example.orderManagementService.customException.*;
import com.example.orderManagementService.models.Order;
import com.example.orderManagementService.models.OrderItem;
import com.example.orderManagementService.repository.OrderItemRepository;
import com.example.orderManagementService.repository.OrderRepository;
import com.example.orderManagementService.utils.JsonOrder;
import com.example.orderManagementService.utils.OrderJsonParser;
import com.example.orderManagementService.utils.InventoryProduct;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Value("${inventory_url}")
    private String INVENTORY_URL;


    private RestTemplate restTemplate;

    public OrderServiceImp(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    @Autowired
    Logger logger;

    @Override
    public Order getById(int orderId) throws OrderNotFoundException {

        Optional<Order> order  = orderRepository.findById(orderId);

        if(order.isPresent()){
            return order.get();
        }

        throw new OrderNotFoundException("Invalid Order Id");
    }

    @Override
    public Page<Order> getAll(int page,int size) {
        PageRequest pr = PageRequest.of(page,size,Sort.by("createdTime").descending());

        return orderRepository.findAll(pr);

    }

    @Override
    public Order postOrder(JsonOrder jsonOrder) throws InvalidQuantityException, NotEnoughQuanityException {
        Order order = OrderJsonParser.extractOrder(jsonOrder);

        List<InventoryProduct> inventoryProducts = OrderJsonParser.extractInventoryProduct(jsonOrder);

//       Check having enough stocks
        boolean result = checkStockInInventory(inventoryProducts);
        if(!result){
            throw new NotEnoughQuanityException("Not enough Stocks in inventory");
        }

        //calculate Total
        calculateTotal(order,inventoryProducts);


        order = orderRepository.save(order);
        List<OrderItem> items = OrderJsonParser.extractItems(jsonOrder,order);
        orderItemRepository.saveAll(items);

//        reduce Stocks
        restTemplate.postForObject(INVENTORY_URL + "/reduce/all", inventoryProducts,Boolean.class);

        return order;
    }

    private void calculateTotal(Order order, List<InventoryProduct> inventoryProducts){
        float total = 0;
        for(InventoryProduct inventoryProduct: inventoryProducts){
            total += (inventoryProduct.getPrice() * inventoryProduct.getQuantity());
        }
        order.setSubTotal(total);
    }

    private boolean checkStockInInventory(List<InventoryProduct> inventoryProducts){

        if(inventoryProducts.isEmpty()){
            return false;
        }
        return restTemplate.postForObject(INVENTORY_URL+"/stock/all", inventoryProducts,Boolean.class);
    }



    @Override
    public List<Order> postOrder(List<JsonOrder> jsonOrders) throws NotEnoughQuanityException, InvalidQuantityException {

        List<Order> orders = new ArrayList<>();
        for(JsonOrder jsonOrder : jsonOrders){
            orders.add(postOrder(jsonOrder));
        }

        return orders;
    }

    @Override
    public void updateInventoryViaKafka(List<InventoryProduct> orderItems) throws JsonProcessingException {

        String payload = objectMapper.writeValueAsString(orderItems);
        kafkaTemplate.send(Constants.TOPIC,UUID.randomUUID().toString(),payload);
        logger.info("Data Published  By KAFKA " + payload);

    }

    @Override
    public Order cancelOrder(int orderId) throws OrderNotFoundException, JsonProcessingException, OrderAlreadyCanceledException {

        Order order = getById(orderId);

        if(order.getStatus().equals(Constants.CANCELED)){
            throw new OrderAlreadyCanceledException("The Order is Already Canceled.");
        }
        List<InventoryProduct> inventoryProducts = OrderJsonParser.extractInventoryProduct(order.getOrderItems());
        updateInventoryViaKafka(inventoryProducts);
        order.setStatus(Constants.CANCELED);
        orderRepository.save(order);
        return order;
    }
}
