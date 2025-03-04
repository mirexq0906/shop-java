package com.example.shop_java.service.order.impl;

import com.example.shop_java.entity.order.Order;
import com.example.shop_java.entity.user.User;
import com.example.shop_java.exception.EntityNotFoundException;
import com.example.shop_java.repository.order.OrderRepository;
import com.example.shop_java.service.event.TrafficEventService;
import com.example.shop_java.service.order.OrderService;
import com.example.shop_java.web.dto.OrderDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProcessor orderProcessor;

    @Override
    public Order findByIdAndUsername(Long id) {
        return this.orderRepository
                .findByIdAndUser_Username(id, ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Transactional
    @Override
    public Order save(OrderDto orderDto) {
        Order order = new Order();
        this.orderProcessor.process(order, orderDto);
        return order;
    }

}
