package com.example.inventoryManagementService;

import com.example.inventoryManagementService.customExceptions.*;
import com.example.inventoryManagementService.models.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {

    Product getById(int productId) throws ProductNotFoundException;

    List<Product> getByName(String name);

    List<Product> searchByName(String query);

    Page<Product> getAll(int page,int size);

    Product postProduct(Product product);

    Iterable<Product> postProduct(List<Product> product);

    Iterable<Product> incrementQuantityViaKafka(String data) throws JsonProcessingException, ProductNotFoundException, InvalidDataProvidedException;

    boolean checkStocks(Product product) throws ProductNotFoundException, NotEnoughQuanityException, InvalidDataProvidedException;
    boolean checkStocks(List<Product> product) throws ProductNotFoundException, NotEnoughQuanityException, InvalidDataProvidedException;

    boolean incrementQuantity(Product product) throws ProductNotFoundException, InvalidDataProvidedException;
    boolean incrementQuantity(List<Product> product) throws ProductNotFoundException, InvalidDataProvidedException;

    boolean reduceQuantity(Product product) throws ProductNotFoundException, NotEnoughQuanityException, InvalidDataProvidedException;
    boolean reduceQuantity(List<Product> product) throws ProductNotFoundException, NotEnoughQuanityException, InvalidDataProvidedException;

    Product putProduct(Product product) throws  InvalidDataProvidedException, ProductNotFoundException;

    Product deleteProduct(int productId) throws ProductNotFoundException;

}
