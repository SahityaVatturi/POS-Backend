package com.example.inventoryManagementService.Authentication;

import com.example.inventoryManagementService.customExceptions.InvalidUserIdException;
import com.example.inventoryManagementService.customExceptions.InvalidUsernameOrPasswordException;
import com.example.inventoryManagementService.models.User;

public interface UserService {
    boolean authenticateUser(String name,String password) throws InvalidUsernameOrPasswordException;
    User getUser(int userId) throws InvalidUserIdException;
    User postUser(User user);

}
