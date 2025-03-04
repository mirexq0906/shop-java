package com.example.shop_java.service.order.impl.step;

import com.example.shop_java.entity.order.Order;
import com.example.shop_java.entity.order.OrderItem;
import com.example.shop_java.message.TrafficMessage;
import com.example.shop_java.service.event.TrafficEventService;
import com.example.shop_java.service.order.OrderProcessingStep;
import com.example.shop_java.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendTrafficStep implements OrderProcessingStep {

    private final TrafficEventService trafficEventService;

    @Override
    public void process(Order order, OrderDto orderDto) {
        for (OrderItem orderItem : order.getOrderItems()) {
            this.trafficEventService.sendEvent(orderItem.getProduct().getId(), TrafficMessage.TrafficType.ORDER);
        }
    }

}
