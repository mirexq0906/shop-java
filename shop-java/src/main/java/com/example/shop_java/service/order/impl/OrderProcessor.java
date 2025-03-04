package com.example.shop_java.service.order.impl;

import com.example.shop_java.entity.order.Order;
import com.example.shop_java.repository.CartRepository;
import com.example.shop_java.repository.ProductRepository;
import com.example.shop_java.repository.order.DeliveryTypeRepository;
import com.example.shop_java.repository.order.OrderRepository;
import com.example.shop_java.repository.order.PaymentMethodRepository;
import com.example.shop_java.service.event.TrafficEventService;
import com.example.shop_java.service.order.OrderProcessingStep;
import com.example.shop_java.service.order.impl.step.*;
import com.example.shop_java.web.dto.OrderDto;
import com.example.shop_java.web.mapper.OrderMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderProcessor {

    private final OrderMapper orderMapper;

    private final ProductRepository productRepository;

    private final DeliveryTypeRepository deliveryTypeRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    private final TrafficEventService trafficService;


    private List<OrderProcessingStep> steps;

    @PostConstruct
    public void init() {
        this.steps = List.of(
                new AssignCustomerStep(orderMapper),
                new AssignProductsStep(orderMapper, productRepository),
                new CountAmountOrderStep(),
                new CheckOrderDetailsAndSaveOrderStep(deliveryTypeRepository, paymentMethodRepository, orderRepository),
                new ClearCartStep(cartRepository),
                new SendTrafficStep(trafficService)
        );
    }

    public void process(Order order, OrderDto orderDto) {
        for (OrderProcessingStep step : steps) {
            step.process(order, orderDto);
        }
    }

}
