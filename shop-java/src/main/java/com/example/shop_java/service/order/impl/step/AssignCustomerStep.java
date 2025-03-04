package com.example.shop_java.service.order.impl.step;

import com.example.shop_java.entity.order.Order;
import com.example.shop_java.entity.order.OrderCustomer;
import com.example.shop_java.entity.user.User;
import com.example.shop_java.service.order.OrderProcessingStep;
import com.example.shop_java.web.dto.OrderDto;
import com.example.shop_java.web.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class AssignCustomerStep implements OrderProcessingStep {

    private final OrderMapper orderMapper;

    @Override
    public void process(Order order, OrderDto orderDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        OrderCustomer orderCustomer = this.orderMapper.requestToOrderCustomer(
                orderDto.getOrderCustomer()
        );

        order.setCustomer(orderCustomer);
        order.setUser(user);
    }

}
