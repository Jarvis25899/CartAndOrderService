package com.example.CartAndOrderService.service;

import com.example.CartAndOrderService.dto.CartOrderDTO;
import com.example.CartAndOrderService.entity.Cart;
import com.example.CartAndOrderService.response.APIResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    Cart addToCart(Cart cart);
    void update(Cart cart);
    void remove(Cart cart);
    List<CartOrderDTO> getCartDetails(String userId);
    ResponseEntity<APIResponse<String>> checkOut(String userId);
    String parseToken(String token);
    String updateCart(String guestId,String userId);
    int cartBadge(String userId);
}
