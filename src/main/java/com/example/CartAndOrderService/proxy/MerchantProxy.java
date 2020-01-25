package com.example.CartAndOrderService.proxy;

import com.example.CartAndOrderService.dto.Merchant;
import com.example.CartAndOrderService.dto.ProductMerchantAvailability;
import com.example.CartAndOrderService.dto.SearchDTO;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("merchantservice")
public interface MerchantProxy {

    @GetMapping("/merchant/getMerchantDetails/{merchantId}")
    Merchant getMerchantDetails(@PathVariable("merchantId") String merchantId);

    @PostMapping("/merchant/updateStock")
    void updateStock(@RequestBody SearchDTO searchDTO);

    @GetMapping("/merchant/productAvailability/{productId}/{merchantId}")
    ProductMerchantAvailability get(@PathVariable("productId") String productId, @PathVariable("merchantId") String merchantId);
}

