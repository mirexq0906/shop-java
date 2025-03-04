package com.example.shop_java.repository;

import com.example.shop_java.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByStoredId(String storedId);

}
