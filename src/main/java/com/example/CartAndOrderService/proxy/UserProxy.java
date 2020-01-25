package com.example.CartAndOrderService.proxy;


import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("loginservice")
public interface UserProxy {

    @GetMapping("/getMail/{userId}")
    String getMail(@PathVariable("userId") String userId);
}
