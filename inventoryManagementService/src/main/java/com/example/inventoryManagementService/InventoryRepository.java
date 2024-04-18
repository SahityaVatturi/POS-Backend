package com.example.inventoryManagementService;

import com.example.inventoryManagementService.models.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface InventoryRepository extends PagingAndSortingRepository<Product, Integer> {
    List<Product> findAllByName(String name);
    List<Product> findAllByNameContainingIgnoreCase(String query,Sort sort);

//    OrderByProgDateAscStartTimeAsc
}
