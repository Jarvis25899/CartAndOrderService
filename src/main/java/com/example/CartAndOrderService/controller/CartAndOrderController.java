package com.example.CartAndOrderService.controller;

import com.example.CartAndOrderService.dto.CartDTO;
import com.example.CartAndOrderService.dto.CartOrderDTO;
import com.example.CartAndOrderService.entity.Cart;
import com.example.CartAndOrderService.entity.Order;
import com.example.CartAndOrderService.service.CartService;
import com.example.CartAndOrderService.service.OrderService;
import com.example.CartAndOrderService.utility.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CartAndOrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addToCart(@RequestBody CartDTO cartDTO){
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDTO,cart);
        Cart cartCreated = cartService.addToCart(cart);

        return new ResponseEntity<Cart>(cartCreated,HttpStatus.CREATED);
    }


    @PostMapping("/buyNow")
    public CartOrderDTO buyNow(@RequestBody CartDTO cartDTO){
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDTO,cart);
        Cart cartCreated = cartService.addToCart(cart);
        return orderService.buyNow(cartCreated);
    }


    //cart button click
    @GetMapping("/cart/{userId}")
    public List<CartOrderDTO> cartDetails(@PathVariable("userId") String userId){
        return cartService.getCartDetails(userId);
    }



    //order details for merchant
    @GetMapping("/orderDetails/{merchantId}")
    public List<Order> orderDetails(@PathVariable("merchantId") String merchantId){
        return orderService.orderDetails(merchantId);
    }



    @GetMapping("/orderHistory/{userId}")
    public List<CartOrderDTO> orderHistory(@PathVariable("userId") String userId){
        return orderService.orderHistory(userId);
    }

    //checkout
    @GetMapping("/checkout/{userId}")
    public List<CartOrderDTO> checkOut(@PathVariable("userId") String userId){
        return cartService.checkOut(userId);
    }


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

    @GetMapping("/productDetails")
    public Product getDetails(){
        return cartService.getDetails();
    }
}
