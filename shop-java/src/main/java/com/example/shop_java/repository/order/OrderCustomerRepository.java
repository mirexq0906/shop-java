package com.example.shop_java.repository.order;

import com.example.shop_java.entity.order.OrderCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCustomerRepository extends JpaRepository<OrderCustomer, Long> {
}
