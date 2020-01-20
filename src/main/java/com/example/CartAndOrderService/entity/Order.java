package com.example.CartAndOrderService.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orderTable")
@Getter @Setter
public class Order {

    @Id
    @GenericGenerator(name = "idGenerator" , strategy = "increment")
    @GeneratedValue(generator = "idGenerator")
    private int orderId;

    private int userId;
    private int merchantId;
    private int productId;
    private long quantity;
    private double price;
    private Date orderDate;
    private double totalPrice;

}
