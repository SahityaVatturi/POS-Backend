package com.example.inventoryManagementService.Authentication;


import com.example.inventoryManagementService.customExceptions.InvalidUsernameOrPasswordException;
import com.example.inventoryManagementService.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Constants.USER_BASE_URL)
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(Constants.USER_LOGIN)
    public boolean getUser(@RequestParam String name,@RequestParam String password){
        try {
            return userService.authenticateUser(name,password);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PostMapping(Constants.USER_REGISTER)
    public User addUser(@RequestBody User user){
        try {
            return userService.postUser(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    @GetMapping(Constants.USER_GET_BY_ID)
    public User addUser(@PathVariable int id){
        try {
            return userService.getUser(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

}
