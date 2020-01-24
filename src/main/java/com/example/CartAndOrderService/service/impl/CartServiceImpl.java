package com.example.CartAndOrderService.service.impl;

import com.example.CartAndOrderService.dto.*;
import com.example.CartAndOrderService.entity.Cart;
import com.example.CartAndOrderService.entity.Order;
import com.example.CartAndOrderService.proxy.CartOrderProxy;
import com.example.CartAndOrderService.proxy.MerchantProxy;
import com.example.CartAndOrderService.proxy.UserProxy;
import com.example.CartAndOrderService.repository.CartRepository;
import com.example.CartAndOrderService.repository.OrderRepository;
import com.example.CartAndOrderService.response.APIResponse;
import com.example.CartAndOrderService.service.CartService;
import com.example.CartAndOrderService.utility.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

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

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    UserProxy userProxy;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String parseToken(String token) {

        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        String id = (String) body.get("userId");
        return id;

    }

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
    public List<CartOrderDTO> getCartDetails(String userId) {

        List<Cart> cartList = (List<Cart>) cartRepository.findAll();
        List<CartOrderDTO> cartOrderDTOList = new ArrayList<>();

        cartList.stream().forEach(cart -> {
            if ((cart.getUserId()).equals(userId)){
                Product productDetails = cartOrderProxy.getProductDetails(cart.getProductId()).getBody().getData();
                Merchant merchantDetails = merchantProxy.getMerchantDetails(cart.getMerchantId());
                ProductMerchantAvailability productMerchantDetails = merchantProxy.get(cart.getProductId(),cart.getMerchantId());

                CartOrderDTO cartOrder = new CartOrderDTO();

                cartOrder.setCartId(cart.getCartId());
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
                cartOrder.setMerchantId(merchantDetails.getMerchantId());

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
    public ResponseEntity<APIResponse<String>> checkOut(String userId) {

        AtomicInteger flag = new AtomicInteger(0);
        List<Cart> cartList = (List<Cart>) cartRepository.findAll();
        List<CartOrderDTO> cartOrderDTOList = new ArrayList<>();
        AtomicLong difference = new AtomicLong(0);
        AtomicReference<String> productName = new AtomicReference<>("");
        SearchDTO searchDTO = new SearchDTO();

        cartList.stream().forEach(cart -> {
            if ((cart.getUserId()).equals(userId)){
                Product productDetails = cartOrderProxy.getProductDetails(cart.getProductId()).getBody().getData();
                Merchant merchantDetails = merchantProxy.getMerchantDetails(cart.getMerchantId());
                ProductMerchantAvailability productMerchantDetails = merchantProxy.get(cart.getProductId(),cart.getMerchantId());
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
                    cartOrder.setMerchantId(merchantDetails.getMerchantId());

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

                        searchDTO.setProductId(order.getProductId());
                        searchDTO.setMerchantId(order.getMerchantId());
                        searchDTO.setPrice(order.getPrice());
                        searchDTO.setProductCategoryId(productDetails.getProductCategoryId());
                        searchDTO.setProductDesc(productDetails.getProductDesc());
                        searchDTO.setProductImage(productDetails.getProductImage());
                        searchDTO.setProductName(productDetails.getProductName());
                        searchDTO.setProductRating(productDetails.getProductRating());
                        searchDTO.setQuantity(order.getQuantity());
                        searchDTO.setSellCount(productDetails.getSellCount() + order.getQuantity());
                        merchantProxy.updateStock(searchDTO);
                        cartOrderProxy.updateSellCount(order.getProductId(),(productDetails.getSellCount() + order.getQuantity()));

                        orderRepository.save(order);

                        MailDTO mailDTO = new MailDTO();

                        mailDTO.setOrderId(order.getOrderId());
                        mailDTO.setUserEmail(userProxy.getMail(userId));
                        mailDTO.setProductName(productDetails.getProductName());
                        mailDTO.setProductDesc(productDetails.getProductDesc());
                        mailDTO.setProductRating(productDetails.getProductRating());
                        mailDTO.setProductImage(productDetails.getProductImage());
                        mailDTO.setMerchantName(merchantDetails.getMerchantName());
                        mailDTO.setQuantity(order.getQuantity());
                        mailDTO.setPrice(order.getPrice());
                        mailDTO.setTotalPrice(order.getTotalPrice());
                        mailDTO.setOrderDate(df.format(date));

                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            kafkaTemplate.send("Mail",objectMapper.writeValueAsString(mailDTO));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }

                    }
                    //cartOrderDTOList.add(cartOrder);
                }
                else {
                    productName.set(productDetails.getProductName());
                    difference.set(cart.getQuantity() - productMerchantDetails.getQuantity());
                    flag.set(1);
                    return;
                }
            }
        });
        if (flag.get() == 0) {
            cartRepository.deleteAll();
            return new ResponseEntity<>(new APIResponse<>(1000,"SUCCESS","Order placed !!!"),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new APIResponse<>(400, productName+" is out of stock by "+difference+" quantity"), HttpStatus.OK);
        }
    }
}
