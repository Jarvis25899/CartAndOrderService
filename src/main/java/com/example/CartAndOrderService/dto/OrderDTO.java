package com.example.CartAndOrderService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class OrderDTO {

    private int orderId;
    private int userId;
    private int merchantId;
    private int productId;
    private long quantity;
    private double price;
    private Date orderDate;
    private double totalPrice;

}
