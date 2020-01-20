package com.example.CartAndOrderService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDTO {

    private int cartId;
    private int userId;
    private int productId;
    private int merchantId;
    private long quantity;

}
