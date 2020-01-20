package com.example.CartAndOrderService.service;

import com.example.CartAndOrderService.entity.Cart;

public interface CartService {
    Cart addToCart(Cart cart);
    void update(Cart cart);
    void remove(Cart cart);
}
