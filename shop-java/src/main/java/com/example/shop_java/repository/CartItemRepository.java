package com.example.shop_java.repository;

import com.example.shop_java.entity.Product;
import com.example.shop_java.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProduct_IdAndCart_Id(Long productId, Long cartId);

    void deleteByProduct_IdAndCart_Id(Long productId, Long cartId);

    boolean existsByProduct_IdAndCart_Id(Long productId, Long cartId);

}
