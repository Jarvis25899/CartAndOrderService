package com.example.CartAndOrderService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CartAndOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartAndOrderServiceApplication.class, args);
	}

}
