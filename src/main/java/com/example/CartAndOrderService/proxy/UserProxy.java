package com.example.CartAndOrderService.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("http://10.177.68.114:8081")
public interface UserProxy {

    @GetMapping("/customer/getMail/{userId}")
    String getMail(@PathVariable("userId") String userId);
}
