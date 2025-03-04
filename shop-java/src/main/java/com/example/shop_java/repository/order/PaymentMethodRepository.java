package com.example.shop_java.repository.order;

import com.example.shop_java.entity.order.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
