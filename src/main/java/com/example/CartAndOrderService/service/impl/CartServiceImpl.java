package com.example.CartAndOrderService.service.impl;

import com.example.CartAndOrderService.dto.CartOrderDTO;
import com.example.CartAndOrderService.dto.Merchant;
import com.example.CartAndOrderService.dto.ProductMerchantAvailability;
import com.example.CartAndOrderService.entity.Cart;
import com.example.CartAndOrderService.entity.Order;
import com.example.CartAndOrderService.proxy.CartOrderProxy;
import com.example.CartAndOrderService.proxy.MerchantProxy;
import com.example.CartAndOrderService.repository.CartRepository;
import com.example.CartAndOrderService.repository.OrderRepository;
import com.example.CartAndOrderService.service.CartService;
import com.example.CartAndOrderService.utility.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CartServiceImpl implements CartService {

    private List<Cart> cartList = new ArrayList<>();

    @Autowired
    CartOrderProxy cartOrderProxy;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MerchantProxy merchantProxy;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Cart addToCart(Cart cart) {
        cartList = (List<Cart>) cartRepository.findAll();
        //System.out.println(cartList);
        AtomicInteger flag = new AtomicInteger(0);
        AtomicInteger id = new AtomicInteger(0);
        AtomicLong updatedQuantity = new AtomicLong(0);
        cartList.stream().forEach(cartObject ->
                {
                    if (((cartObject.getUserId()).equals(cart.getUserId())) && (((cartObject.getProductId()).equals(cart.getProductId())) && ((cartObject.getMerchantId()).equals(cart.getMerchantId())))) {
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


    @Override
    public Product getDetails() {
        return cartOrderProxy.getProductDetails("1");
    }

    @Override
    public List<CartOrderDTO> getCartDetails(String userId) {

        List<Cart> cartList = (List<Cart>) cartRepository.findAll();
        List<CartOrderDTO> cartOrderDTOList = new ArrayList<>();

        cartList.stream().forEach(cart -> {
            if ((cart.getUserId()).equals(userId)){
                Product productDetails = cartOrderProxy.getProductDetails(cart.getProductId());
                Merchant merchantDetails = merchantProxy.getMerchantDetails(cart.getMerchantId());
                ProductMerchantAvailability productMerchantDetails = merchantProxy.getProductAvailability(cart.getProductId(),cart.getMerchantId());

                CartOrderDTO cartOrder = new CartOrderDTO();
                cartOrder.setProductId(productDetails.getProductId());
                cartOrder.setProductName(productDetails.getProductName());
                cartOrder.setProductDesc(productDetails.getProductDesc());
                cartOrder.setProductCategoryId(productDetails.getProductCategoryId());
                cartOrder.setProductRating(productDetails.getProductRating());
                cartOrder.setProductImage(productDetails.getProductImage());
                cartOrder.setSellCount(productDetails.getSellCount());
                cartOrder.setMerchantName(merchantDetails.getMerchantName());
                cartOrder.setPrice(productMerchantDetails.getPrice());
                cartOrder.setQuantity(cart.getQuantity());
                double totalPrice = productMerchantDetails.getPrice() * cart.getQuantity();
                cartOrder.setTotalPrice(totalPrice);
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                cartOrder.setOrderDate(df.format(date));
                cartOrderDTOList.add(cartOrder);
            }
        });

        return cartOrderDTOList;
    }

    @Override
    public List<CartOrderDTO> checkOut(String userId) {

        AtomicInteger flag = new AtomicInteger(0);
        List<Cart> cartList = (List<Cart>) cartRepository.findAll();
        List<CartOrderDTO> cartOrderDTOList = new ArrayList<>();

        cartList.stream().forEach(cart -> {
            if ((cart.getUserId()).equals(userId)){
                Product productDetails = cartOrderProxy.getProductDetails(cart.getProductId());
                Merchant merchantDetails = merchantProxy.getMerchantDetails(cart.getMerchantId());
                ProductMerchantAvailability productMerchantDetails = merchantProxy.getProductAvailability(cart.getProductId(),cart.getMerchantId());
                if (cart.getQuantity() < productMerchantDetails.getQuantity()){

                    CartOrderDTO cartOrder = new CartOrderDTO();
                    cartOrder.setProductId(productDetails.getProductId());
                    cartOrder.setProductName(productDetails.getProductName());
                    cartOrder.setProductDesc(productDetails.getProductDesc());
                    cartOrder.setProductCategoryId(productDetails.getProductCategoryId());
                    cartOrder.setProductRating(productDetails.getProductRating());
                    cartOrder.setProductImage(productDetails.getProductImage());
                    cartOrder.setSellCount(productDetails.getSellCount());
                    cartOrder.setMerchantName(merchantDetails.getMerchantName());
                    cartOrder.setPrice(productMerchantDetails.getPrice());

                    cartOrder.setQuantity(cart.getQuantity());
                    double totalPrice = productMerchantDetails.getPrice() * cart.getQuantity();
                    cartOrder.setTotalPrice(totalPrice);
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    cartOrder.setOrderDate(df.format(date));
                    if (flag.get() == 0) {
                        Order order = new Order();
                        order.setMerchantId(merchantDetails.getMerchantId());
                        order.setProductId(cart.getProductId());
                        order.setQuantity(cart.getQuantity());
                        order.setUserId(cart.getUserId());
                        order.setPrice(productMerchantDetails.getPrice());

                        order.setOrderDate(df.format(date));
                        order.setTotalPrice(totalPrice);

                        orderRepository.save(order);
                    }
                    cartOrderDTOList.add(cartOrder);

                }
                else {
                    flag.set(1);
                    return;
                }
            }
        });
        if (flag.get() == 0) {
            cartRepository.deleteAll();
            return cartOrderDTOList;
        }
        return null;
    }
}
