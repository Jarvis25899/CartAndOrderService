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

    private int userId;
    private int productId;
    private int merchantId;

    @Column(columnDefinition = "integer default 1")
    private long quantity;

}
