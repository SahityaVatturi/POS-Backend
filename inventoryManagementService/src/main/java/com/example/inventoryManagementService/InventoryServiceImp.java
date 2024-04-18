package com.example.inventoryManagementService;

import com.example.inventoryManagementService.customExceptions.*;
import com.example.inventoryManagementService.models.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class InventoryServiceImp implements InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Logger logger;

    @Override
    public Product getById(int productId) throws ProductNotFoundException {

        Optional<Product> product = inventoryRepository.findById(productId);

        if (!product.isPresent()) {
            throw new ProductNotFoundException("Product Not Found");
        }

        return product.get();

    }

    @Override
    public List<Product> getByName(String name) {
        return inventoryRepository.findAllByName(name);
    }

    @Override
    public List<Product> searchByName(String query) {

        return inventoryRepository.findAllByNameContainingIgnoreCase(query,Sort.by(Sort.Direction.ASC,"name").and(Sort.by(Sort.Direction.DESC,"quantity")));
    }

    @Override
    public Page<Product> getAll(int page, int size) {
        PageRequest pr = PageRequest.of(page,size,Sort.by("quantity").descending());
        return inventoryRepository.findAll(pr);
    }

    @Override
    public Product postProduct(Product product) {
        return inventoryRepository.save(product);
    }

    @Override
    public Iterable<Product> postProduct(List<Product> products) {
        return inventoryRepository.saveAll(products);
    }

    @Override
    public List<Product> incrementQuantityViaKafka(String message)
            throws JsonProcessingException, ProductNotFoundException, InvalidDataProvidedException {

        TypeReference<List<Product>> ref = new TypeReference<List<Product>>() {
        };

        List<Product> orderedProducts = objectMapper.readValue(message, ref);
        incrementQuantity(orderedProducts);

        return orderedProducts;
    }

    @Override
    public boolean checkStocks(Product orderedProduct) throws ProductNotFoundException, NotEnoughQuanityException, InvalidDataProvidedException {


        checkValidQuantityProvided(orderedProduct);

        Optional<Product> result = inventoryRepository.findById(orderedProduct.getId());

        if (!result.isPresent()) {
            throw new ProductNotFoundException("Invalid Product Id with " + orderedProduct.getId());
        }

        Product originalProduct = result.get();

        if (originalProduct.getQuantity() < orderedProduct.getQuantity()) {
            throw new NotEnoughQuanityException("Not enough Quantity");
        }

        return true;
    }

    @Override
    public boolean checkStocks(List<Product> products) throws ProductNotFoundException, NotEnoughQuanityException, InvalidDataProvidedException {

        for(Product product :products){
            checkStocks(product);
        }
        return true;
    }

    @Override
    public boolean incrementQuantity(Product product) throws ProductNotFoundException, InvalidDataProvidedException {

        checkValidQuantityProvided(product);
        updateQuantity(product,true);
        return true;
    }

    @Override
    public boolean incrementQuantity(List<Product> products) throws ProductNotFoundException, InvalidDataProvidedException {

        checkValidQuantityProvided(products);

        for(Product product:products){
            updateQuantity(product,true);
        }

        return true;
    }

    @Override
    public boolean reduceQuantity(Product product) throws ProductNotFoundException, NotEnoughQuanityException, InvalidDataProvidedException {

        checkValidQuantityProvided(product);
        checkStocks(product);

        updateQuantity(product,false);

        return true;
    }

    @Override
    public boolean reduceQuantity(List<Product> products) throws ProductNotFoundException, NotEnoughQuanityException, InvalidDataProvidedException {
        checkStocks(products);

        for(Product product:products){
            updateQuantity(product,false);
        }

        return true;
    }

    private void updateQuantity(Product orderedProduct, boolean isIncrement) throws ProductNotFoundException, InvalidDataProvidedException {

        checkValidQuantityProvided(orderedProduct);
        Product originalProduct = getById(orderedProduct.getId());

        int updatedQuantity;
        if(isIncrement){
            updatedQuantity = originalProduct.getQuantity() + orderedProduct.getQuantity();
        }else {
            updatedQuantity = originalProduct.getQuantity() - orderedProduct.getQuantity();
        }

        originalProduct.setQuantity(updatedQuantity);

        inventoryRepository.save(originalProduct);
    }


    private boolean checkValidQuantityProvided(Product product) throws InvalidDataProvidedException {

        if(product.getQuantity() < 0){
            throw new InvalidDataProvidedException("Quantity Should be Positive number");
        }
        return true;
    }

    private boolean checkValidQuantityProvided(List<Product> products) throws InvalidDataProvidedException {

        for(Product product : products){
            checkValidQuantityProvided(product);
        }

        return true;
    }

    @Override
    public Product putProduct(Product product) throws ProductNotFoundException, InvalidDataProvidedException {




        Product modifiedProduct = getById(product.getId());

        checkValidQuantityProvided(product);

        modifiedProduct.setId(product.getId());
        modifiedProduct.setName(product.getName());
        modifiedProduct.setPrice(product.getPrice());
        modifiedProduct.setImageUrl(product.getImageUrl());
        modifiedProduct.setQuantity(product.getQuantity());


        return inventoryRepository.save(modifiedProduct);
    }

    @Override
    public Product deleteProduct(int productId) throws ProductNotFoundException {
        Product result = getById(productId);
        inventoryRepository.deleteById(productId);

        return result;
    }
}
