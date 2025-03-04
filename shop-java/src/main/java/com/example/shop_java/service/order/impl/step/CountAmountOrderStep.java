package com.example.shop_java.service.order.impl.step;

import com.example.shop_java.entity.order.Order;
import com.example.shop_java.entity.order.OrderItem;
import com.example.shop_java.service.order.OrderProcessingStep;
import com.example.shop_java.web.dto.OrderDto;

public class CountAmountOrderStep implements OrderProcessingStep {

    @Override
    public void process(Order order, OrderDto orderDto) {
        Long amount = order.getOrderItems().stream()
                .map(OrderItem::getPrice)
                .reduce(0L, Long::sum);

        order.setAmount(amount);
    }

}
