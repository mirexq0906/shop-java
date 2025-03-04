package com.example.shop_java.repository.order;

import com.example.shop_java.entity.order.Order;
import com.example.shop_java.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndUser_Username(Long id, String username);

    List<Order> User(User user);
}
