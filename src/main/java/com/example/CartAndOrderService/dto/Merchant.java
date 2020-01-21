package com.example.CartAndOrderService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Merchant {

    private String merchantId;
    private String merchantName;
    private String mobileNo;
    private double merchantRating;
    private String merchantAddress;
    private String merchantEmail;
    private String merchantPassword;
    private String merchantImage;

}
