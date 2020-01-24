package com.example.CartAndOrderService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDTO {

    private int cartId;

    private String token;
    private String userId;
    private String productId;
    private String merchantId;
    private long quantity;

}
