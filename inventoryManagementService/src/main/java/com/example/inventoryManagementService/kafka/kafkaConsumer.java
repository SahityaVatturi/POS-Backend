package com.example.inventoryManagementService.kafka;

import com.example.inventoryManagementService.Authentication.Constants;
import com.example.inventoryManagementService.InventoryService;
import com.example.inventoryManagementService.customExceptions.NotEnoughQuanityException;
import com.example.inventoryManagementService.customExceptions.ProductNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class kafkaConsumer {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private Logger logger;


    @KafkaListener(topics=Constants.KAFKA_TOPIC, groupId = Constants.KAFKA_GROUP_ID)
    public void consume(String message){
        try {
            logger.info("Data Consumed By KAFKA "+ message);
            inventoryService.incrementQuantityViaKafka(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
