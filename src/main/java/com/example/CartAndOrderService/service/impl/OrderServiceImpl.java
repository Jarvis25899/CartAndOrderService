package com.example.CartAndOrderService.service.impl;

import com.example.CartAndOrderService.dto.CartOrderDTO;
import com.example.CartAndOrderService.dto.Merchant;
import com.example.CartAndOrderService.dto.OrderDetail;
import com.example.CartAndOrderService.dto.ProductMerchantAvailability;
import com.example.CartAndOrderService.entity.Cart;
import com.example.CartAndOrderService.entity.Order;
import com.example.CartAndOrderService.proxy.CartOrderProxy;
import com.example.CartAndOrderService.proxy.MerchantProxy;
import com.example.CartAndOrderService.repository.OrderRepository;
import com.example.CartAndOrderService.service.OrderService;
import com.example.CartAndOrderService.utility.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartOrderProxy cartOrderProxy;

    @Autowired
    MerchantProxy merchantProxy;



    @Override
    public CartOrderDTO buyNow(Cart cartCreated) {

        Order order = new Order();

        Product productDetails = cartOrderProxy.getProductDetails(cartCreated.getProductId()).getBody().getData();
        Merchant merchantDetails = merchantProxy.getMerchantDetails(cartCreated.getMerchantId());
        ProductMerchantAvailability productMerchantDetails = merchantProxy.get(cartCreated.getProductId(),cartCreated.getMerchantId());

        order.setMerchantId(cartCreated.getMerchantId());
        order.setProductId(cartCreated.getProductId());
        order.setQuantity(cartCreated.getQuantity());
        order.setUserId(cartCreated.getUserId());
        order.setPrice(productMerchantDetails.getPrice());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        order.setOrderDate(df.format(date));

        double totalPrice = productMerchantDetails.getPrice() * order.getQuantity();
        order.setTotalPrice(totalPrice);


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
        cartOrder.setQuantity(order.getQuantity());
        cartOrder.setTotalPrice(totalPrice);
        cartOrder.setOrderDate(df.format(date));

        return cartOrder;
    }

    @Override
    public List<OrderDetail> orderDetails(String merchantId) {
        List<Order> orderList = (List<Order>) orderRepository.findAll();
        List<OrderDetail> orders = new ArrayList<>();
        orderList.stream().forEach(order -> {
            if ((order.getMerchantId()).equals(merchantId)){
                Product productDetails = cartOrderProxy.getProductDetails(order.getProductId()).getBody().getData();
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setMerchantId(order.getMerchantId());
                orderDetail.setOrderDate(order.getOrderDate());
                orderDetail.setOrderId(order.getOrderId());
                orderDetail.setPrice(order.getPrice());
                orderDetail.setProductId(order.getProductId());
                orderDetail.setProductImage(productDetails.getProductImage());
                orderDetail.setProductName(productDetails.getProductName());
                orderDetail.setQuantity(order.getQuantity());
                orderDetail.setTotalPrice(order.getTotalPrice());
                orderDetail.setUserId(order.getUserId());

                orders.add(orderDetail);
            }
        });
        return orders;
    }

    @Override
    public List<CartOrderDTO> orderHistory(String userId) {
        List<Order> orderList = (List<Order>) orderRepository.findAll();
        List<CartOrderDTO> cartOrderDTOList = new ArrayList<>();
        orderList.stream().forEach(order -> {
            if ((order.getUserId()).equals(userId)) {
                Product productDetails = cartOrderProxy.getProductDetails(order.getProductId()).getBody().getData();
                Merchant merchantDetails = merchantProxy.getMerchantDetails(order.getMerchantId());
                ProductMerchantAvailability productMerchantDetails = merchantProxy.get(order.getProductId(), order.getMerchantId());

                CartOrderDTO cartOrder = new CartOrderDTO();
                cartOrder.setOrderId(order.getOrderId());
                cartOrder.setProductId(productDetails.getProductId());
                cartOrder.setProductName(productDetails.getProductName());
                cartOrder.setProductDesc(productDetails.getProductDesc());
                cartOrder.setProductCategoryId(productDetails.getProductCategoryId());
                cartOrder.setProductRating(productDetails.getProductRating());
                cartOrder.setProductImage(productDetails.getProductImage());
                cartOrder.setSellCount(productDetails.getSellCount());
                cartOrder.setMerchantName(merchantDetails.getMerchantName());
                cartOrder.setPrice(productMerchantDetails.getPrice());
                cartOrder.setQuantity(order.getQuantity());
                cartOrder.setTotalPrice(order.getTotalPrice());
                cartOrder.setOrderDate(order.getOrderDate());

                cartOrderDTOList.add(cartOrder);
            }
        });
        return cartOrderDTOList;
    }

}
