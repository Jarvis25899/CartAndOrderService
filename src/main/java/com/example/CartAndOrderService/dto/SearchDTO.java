package com.example.CartAndOrderService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchDTO {

    private String productId;
    private String productName;
    private String productDesc;
    private String productCategoryId;
    private double productRating;
    private String productImage;
    private long sellCount;
    private String merchantId;
    private long quantity;
    private double price;

}
