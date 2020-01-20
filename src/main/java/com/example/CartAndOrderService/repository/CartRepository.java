package com.example.CartAndOrderService.repository;

import com.example.CartAndOrderService.entity.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CartRepository extends CrudRepository<Cart,Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Cart SET quantity = :#{#value} WHERE user_id = :#{#userId} AND product_id = :#{#productId} AND merchant_id = :#{#merchantId}")
    void update(@Param("value") long value, @Param("userId") int userId, @Param("productId") int productId, @Param("merchantId") int merchantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cart WHERE user_id = :#{#userId} AND product_id = :#{#productId} AND merchant_id = :#{#merchantId} AND quantity = :#{#value}")
    void deleteProduct(@Param("userId") int userId, @Param("productId") int productId, @Param("merchantId") int merchantId, @Param("value") long value);

}
