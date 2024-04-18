package com.example.orderManagementService;

import com.example.orderManagementService.customException.InvalidQuantityException;
import com.example.orderManagementService.customException.NotEnoughQuanityException;
import com.example.orderManagementService.customException.OrderAlreadyCanceledException;
import com.example.orderManagementService.customException.OrderNotFoundException;
import com.example.orderManagementService.models.Order;
import com.example.orderManagementService.utils.JsonOrder;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping(Constants.ORDER_BASE_URL)
public class OrderController {

    @Autowired
    OrderService orderService;


    @GetMapping(Constants.ORDER_GET_ALL)
    public Page<Order> getAll(@RequestParam int page,@RequestParam int size){
        try {
            return orderService.getAll(page,size);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping(Constants.ORDER_GET_BY_ID)
    public Order getOrderById(@PathVariable int id){

        try {
            return orderService.getById(id);
        } catch (OrderNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }

    }


    @PostMapping(Constants.ORDER_ADD)
    public Order postOrder(@RequestBody JsonOrder jsonOrder){

        try {
            return orderService.postOrder(jsonOrder);
        } catch (InvalidQuantityException | NotEnoughQuanityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }

    }


    @PostMapping(Constants.ORDER_ADD_ALL)
    public List<Order> postAllOrder(@RequestBody List<JsonOrder> orders){
        try {
            return orderService.postOrder(orders);
        } catch (InvalidQuantityException | NotEnoughQuanityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }


    @GetMapping(Constants.ORDER_CANCEL)
    public Order cancelOrder(@PathVariable int orderId){
        try {
            return orderService.cancelOrder(orderId);
        } catch (OrderAlreadyCanceledException | OrderNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }


}
