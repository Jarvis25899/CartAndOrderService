package com.example.CartAndOrderService.service.impl;

import com.example.CartAndOrderService.entity.Cart;
import com.example.CartAndOrderService.repository.CartRepository;
import com.example.CartAndOrderService.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CartServiceImpl implements CartService {

    private List<Cart> cartList = new ArrayList<>();

    @Autowired
    CartRepository cartRepository;

    @Override
    public Cart addToCart(Cart cart) {
        cartList = (List<Cart>) cartRepository.findAll();
        System.out.println(cartList);
        AtomicInteger flag = new AtomicInteger(0);
        AtomicInteger id = new AtomicInteger(0);
        AtomicLong updatedQuantity = new AtomicLong(0);
        cartList.stream().forEach(cartObject ->
                {
                    if ((cartObject.getUserId() == cart.getUserId()) && ((cartObject.getProductId() == cart.getProductId()) && (cartObject.getMerchantId() == cart.getMerchantId()))) {
                        flag.set(1);
                        cartRepository.update((cart.getQuantity() + cartObject.getQuantity()) , cartObject.getUserId() , cartObject.getProductId() , cartObject.getMerchantId());
                        id.set(cartObject.getCartId());
                        updatedQuantity.set(cartObject.getQuantity());
                    }
                }
        );
        if (flag.get()==1){
            long quantity = cart.getQuantity();
            cart.setCartId(id.get());
            cart.setQuantity(quantity + updatedQuantity.get());
            return cart;
        }
        return cartRepository.save(cart);
    }

    @Override
    public void update(Cart cart) {
        cartRepository.update(cart.getQuantity(),cart.getUserId(),cart.getProductId(),cart.getMerchantId());
    }

    @Override
    public void remove(Cart cart) {
        cartRepository.deleteProduct(cart.getUserId(),cart.getProductId(),cart.getMerchantId(),cart.getQuantity());
    }

    //Buy Now


    //Cart button
    //need to call product and merchant both services
}
