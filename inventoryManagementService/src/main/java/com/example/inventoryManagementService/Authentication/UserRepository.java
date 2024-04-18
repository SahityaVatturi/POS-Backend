package com.example.inventoryManagementService.Authentication;

import com.example.inventoryManagementService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByNameAndPassword(String name,String password);
}
