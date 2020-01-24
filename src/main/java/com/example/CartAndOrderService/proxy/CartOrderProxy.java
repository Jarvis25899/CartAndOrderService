package com.example.CartAndOrderService.proxy;

import com.example.CartAndOrderService.response.APIResponse;
import com.example.CartAndOrderService.utility.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "http://localhost:8080/")
public interface CartOrderProxy {

    @GetMapping("details/{id}")
    ResponseEntity<APIResponse<Product>> getProductDetails(@PathVariable("id") String id);

    @GetMapping("/updateSellCount/{productId}/{sellCount}")
    void updateSellCount(@PathVariable("productId") String productId,@PathVariable("sellCount") long sellCount);
}
