package com.example.CartAndOrderService.service;

import com.example.CartAndOrderService.dto.CartOrderDTO;
import com.example.CartAndOrderService.dto.OrderDetail;
import com.example.CartAndOrderService.entity.Cart;

import java.util.List;

public interface OrderService {
    CartOrderDTO buyNow(Cart cartCreated);
    List<OrderDetail> orderDetails(String merchantId);
    List<CartOrderDTO> orderHistory(String userId);
}
