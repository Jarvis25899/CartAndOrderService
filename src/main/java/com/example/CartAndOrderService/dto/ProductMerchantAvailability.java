package com.example.CartAndOrderService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductMerchantAvailability {

    private int ProductMerchantId;
    private String productId;
    private String merchantId;
    private long quantity;
    private double price;

}
