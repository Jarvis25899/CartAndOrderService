package com.example.CartAndOrderService.proxy;

import com.example.CartAndOrderService.response.APIResponse;
import com.example.CartAndOrderService.utility.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("productservice")
public interface CartOrderProxy {

    @GetMapping("/product/details/{id}")
    ResponseEntity<APIResponse<Product>> getProductDetails(@PathVariable("id") String id);

    @GetMapping("/product/updateSellCount/{productId}/{sellCount}")
    void updateSellCount(@PathVariable("productId") String productId,@PathVariable("sellCount") long sellCount);
}
