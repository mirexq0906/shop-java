package com.example.shop_java.service.order;

import com.example.shop_java.entity.order.Order;
import com.example.shop_java.web.dto.OrderDto;

public interface OrderProcessingStep {

    void process(Order order, OrderDto orderDto);

}
