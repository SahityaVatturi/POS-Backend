package com.example.orderManagementService.repository;

import com.example.orderManagementService.models.Order;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface OrderRepository extends PagingAndSortingRepository<Order,Integer> {
}
