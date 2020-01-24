package com.example.CartAndOrderService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MailDTO {

    private int orderId;
    private String userEmail;
    private String productName;
    private String productDesc;
    private double productRating;
    private String productImage;
    private String merchantName;
    private long quantity;
    private double price;
    private double totalPrice;
    private String orderDate;

}
