package com.example.CartAndOrderService.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Cart")
@Getter @Setter
public class Cart {

    @Id
    @GenericGenerator(name = "idGenerator" , strategy = "increment")
    @GeneratedValue(generator = "idGenerator")
    private int cartId;

    private String userId;
    private String productId;
    private String merchantId;
    private long quantity;

}
