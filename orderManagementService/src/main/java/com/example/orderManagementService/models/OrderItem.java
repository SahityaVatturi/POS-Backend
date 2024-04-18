package com.example.orderManagementService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;


@Data
@ToString
@Entity
@Table(name = "order_item"
        ,uniqueConstraints =  @UniqueConstraint(columnNames = {"order_id","product_id"})
 )
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    @Column(nullable = false)
    private int product_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id",nullable = false)
    private Order order;

}