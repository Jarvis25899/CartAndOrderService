package com.example.CartAndOrderService.proxy;

import com.example.CartAndOrderService.utility.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "http://localhost:8080/")
public interface CartOrderProxy {

    @GetMapping("details/{id}")
    Product getProductDetails(@PathVariable("id") String id);

}
