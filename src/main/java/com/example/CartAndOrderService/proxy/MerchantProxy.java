package com.example.CartAndOrderService.proxy;

import com.example.CartAndOrderService.dto.Merchant;
import com.example.CartAndOrderService.dto.ProductMerchantAvailability;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "http://localhost:8082/")
public interface MerchantProxy {

    @GetMapping("profile/{id}")
    Merchant getMerchantDetails(@PathVariable("id") String id);

    @GetMapping("productMerchantAvailability/{productId}/{merchantId}")
    ProductMerchantAvailability getProductAvailability(@PathVariable("productId") String productId, @PathVariable("merchantId") String merchantId);
}

