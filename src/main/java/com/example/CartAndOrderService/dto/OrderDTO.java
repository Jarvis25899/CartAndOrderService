package com.example.CartAndOrderService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class OrderDTO {

    private int orderId;
    private String userId;
    private String merchantId;
    private String productId;
    private long quantity;
    private double price;
    private String orderDate;
    private double totalPrice;

}
