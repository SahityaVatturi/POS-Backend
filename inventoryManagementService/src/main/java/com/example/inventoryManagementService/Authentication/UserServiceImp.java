package com.example.inventoryManagementService.Authentication;

import com.example.inventoryManagementService.customExceptions.InvalidUserIdException;
import com.example.inventoryManagementService.customExceptions.InvalidUsernameOrPasswordException;
import com.example.inventoryManagementService.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean authenticateUser(String name, String password) throws InvalidUsernameOrPasswordException {

        User user = userRepository.findByNameAndPassword(name,password);

        if (user == null){
            throw new InvalidUsernameOrPasswordException("Invalid Username Or Password");
        }
        return true;
    }

    @Override
    public User getUser(int userId) throws InvalidUserIdException {
        Optional<User> result = userRepository.findById(userId);
        if(!result.isPresent()){
            throw new InvalidUserIdException("Invalid User Id");
        }
        return result.get();
    }

    @Override
    public User postUser(User user) {

        return userRepository.save(user);
    }
}
