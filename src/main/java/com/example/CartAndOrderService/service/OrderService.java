package com.example.CartAndOrderService.service;

import com.example.CartAndOrderService.dto.CartOrderDTO;
import com.example.CartAndOrderService.entity.Cart;
import com.example.CartAndOrderService.entity.Order;

import java.util.List;

public interface OrderService {
    CartOrderDTO buyNow(Cart cartCreated);
    List<Order> orderDetails(String merchantId);
    List<CartOrderDTO> orderHistory(String userId);
}
