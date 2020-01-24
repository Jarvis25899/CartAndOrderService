package com.example.CartAndOrderService.controller;

import com.example.CartAndOrderService.dto.CartDTO;
import com.example.CartAndOrderService.dto.CartOrderDTO;
import com.example.CartAndOrderService.dto.OrderDetail;
import com.example.CartAndOrderService.entity.Cart;
import com.example.CartAndOrderService.entity.Order;
import com.example.CartAndOrderService.response.APIResponse;
import com.example.CartAndOrderService.service.CartService;
import com.example.CartAndOrderService.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartAndOrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;


    @PostMapping("/addToCart")
    public ResponseEntity<APIResponse<String>> addToCart(@RequestBody CartDTO cartDTO){
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDTO,cart);
        String userId = cartService.parseToken(cartDTO.getToken());
        cart.setUserId(userId);
        cartService.addToCart(cart);

        return new ResponseEntity<>(new APIResponse<>(1000,"SUCCESS","Item Added To Cart"),HttpStatus.OK);
    }

    @PostMapping("/buyNow")
    public ResponseEntity<APIResponse<CartOrderDTO>> buyNow(@RequestBody CartDTO cartDTO){
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDTO,cart);
        String userId = cartService.parseToken(cartDTO.getToken());
        cart.setUserId(userId);
        Cart cartCreated = cartService.addToCart(cart);

        return new ResponseEntity<>(new APIResponse<>(1000,"SUCCESS",orderService.buyNow(cartCreated)),HttpStatus.OK);
    }

    @GetMapping("/cart/{token}")
    public ResponseEntity<APIResponse<List<CartOrderDTO>>> cartDetails(@PathVariable("token") String token){
        return new ResponseEntity<>(new APIResponse(1000,"SUCCESS",cartService.getCartDetails(cartService.parseToken(token))),HttpStatus.OK);
    }


    @GetMapping("/orderDetails/{token}")
    public ResponseEntity<APIResponse<List<OrderDetail>>> orderDetails(@PathVariable("token") String token){
        return new ResponseEntity<>(new APIResponse<>(1000,"SUCCESS",orderService.orderDetails(cartService.parseToken(token))),HttpStatus.OK);
    }


    @GetMapping("/orderHistory/{token}")
    public ResponseEntity<APIResponse<List<CartOrderDTO>>> orderHistory(@PathVariable("token") String token){
        return new ResponseEntity<>(new APIResponse<>(1000,"SUCCESS",orderService.orderHistory(cartService.parseToken(token))),HttpStatus.OK);

    }

    @GetMapping("/checkout/{token}")
    public ResponseEntity<APIResponse<String>> checkOut(@PathVariable("token") String token){
        return cartService.checkOut(cartService.parseToken(token));
    }


    @PostMapping("/updateQuantity")
    public ResponseEntity<APIResponse<String>> updateQuantity(@RequestBody CartDTO cartDTO){
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDTO,cart);
        String userId = cartService.parseToken(cartDTO.getToken());
        cart.setUserId(userId);
        cartService.update(cart);
        return new ResponseEntity<>(new APIResponse<>(1000,"SUCCESS","Quantity updated"),HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<APIResponse<String>> remove(@RequestBody CartDTO cartDTO){
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDTO,cart);
        String userId = cartService.parseToken(cartDTO.getToken());
        cart.setUserId(userId);
        cartService.remove(cart);
        return new ResponseEntity<>(new APIResponse<>(1000,"SUCCESS","Item removed from cart"),HttpStatus.OK);
    }
}
