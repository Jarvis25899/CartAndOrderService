package com.example.CartAndOrderService.repository;

import com.example.CartAndOrderService.entity.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CartRepository extends CrudRepository<Cart,Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Cart SET quantity = ?1 WHERE user_id = ?2 AND product_id = ?3 AND merchant_id = ?4")
    void update(long value, String userId, String productId, String merchantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cart WHERE user_id = ?1 AND product_id = ?2 AND merchant_id = ?3 AND quantity = ?4")
    void deleteProduct(String userId, String productId, String merchantId, long value);

    @Transactional
    @Modifying
    @Query("UPDATE Cart SET user_id = ?2 WHERE user_id = ?1")
    void updateCart(String guestId,String userId);

}
