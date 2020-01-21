package com.example.CartAndOrderService.service;

import com.example.CartAndOrderService.dto.CartOrderDTO;
import com.example.CartAndOrderService.entity.Cart;
import com.example.CartAndOrderService.utility.Product;

import java.util.List;

public interface CartService {
    Cart addToCart(Cart cart);
    void update(Cart cart);
    void remove(Cart cart);
    Product getDetails();
    List<CartOrderDTO> getCartDetails(String userId);
    List<CartOrderDTO> checkOut(String userId);
}
