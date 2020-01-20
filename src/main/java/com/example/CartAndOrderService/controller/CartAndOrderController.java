package com.example.CartAndOrderService.controller;

import com.example.CartAndOrderService.dto.CartDTO;
import com.example.CartAndOrderService.entity.Cart;
import com.example.CartAndOrderService.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartOrder")
public class CartAndOrderController {

    @Autowired
    private CartService cartService;


    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addToCart(@RequestBody CartDTO cartDTO){
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDTO,cart);
        Cart cartCreated = cartService.addToCart(cart);

        return new ResponseEntity<Cart>(cartCreated,HttpStatus.CREATED);
    }


    //BuyNow functionality
    //Need to check for out of stock so need to call merchant service

//    @GetMapping("/buyNow")
//    public ResponseEntity<Integer> buyNow(@RequestBody CartDTO cartDTO){
//        Cart cart = new Cart();
//        BeanUtils.copyProperties(cartDTO,cart);
//        Cart cartCreated = cartService.addToCart(cart);
//
//
//        return new ResponseEntity<Integer>(cartCreated.getUserId(),HttpStatus.CREATED);
//    }

    //cart button click
    //Need to check for out of stock so need to call merchant service

    @PostMapping("/updateQuantity")
    public void updateQuantity(@RequestBody CartDTO cartDTO){
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDTO,cart);
        cartService.update(cart);
    }

    @DeleteMapping("/remove")
    public void remove(@RequestBody CartDTO cartDTO){
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDTO,cart);
        cartService.remove(cart);
    }
}
