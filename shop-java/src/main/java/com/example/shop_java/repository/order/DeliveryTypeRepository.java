package com.example.shop_java.repository.order;

import com.example.shop_java.entity.order.DeliveryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Long> {
}
