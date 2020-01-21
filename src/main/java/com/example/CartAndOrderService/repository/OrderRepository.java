package com.example.CartAndOrderService.repository;

import com.example.CartAndOrderService.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order,Integer> {

//    @Query(value = "SELECT order FROM Order order WHERE order.merchant_id = ?1",nativeQuery = true)
//    List<Order> orderDetails(String merchantId);
}
