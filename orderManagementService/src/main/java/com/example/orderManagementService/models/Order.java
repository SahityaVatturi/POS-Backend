package com.example.orderManagementService.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
@Data
@ToString
@Table(name = "order_t")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private float tax;
    @Column
    private float subTotal;
    @Column
    private String status;
    @Column
    private String paymentMethod;

    @CreationTimestamp
    @Column
    private Date createdTime;

//    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;


}