package com.example.CartAndOrderService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderDetail {

    private int orderId;
    private String token;
    private String userId;
    private String merchantId;
    private String productId;
    private String productName;
    private String productImage;
    private long quantity;
    private double price;
    private String orderDate;
    private double totalPrice;
}
