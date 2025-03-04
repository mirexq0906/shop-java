package com.example.shop_java.service.order.impl.step;

import com.example.shop_java.entity.Product;
import com.example.shop_java.entity.order.Order;
import com.example.shop_java.entity.order.OrderItem;
import com.example.shop_java.exception.OrderCreateException;
import com.example.shop_java.repository.ProductRepository;
import com.example.shop_java.service.order.OrderProcessingStep;
import com.example.shop_java.web.dto.OrderDto;
import com.example.shop_java.web.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AssignProductsStep implements OrderProcessingStep {

    private final OrderMapper orderMapper;

    private final ProductRepository productRepository;

    @Override
    public void process(Order order, OrderDto orderDto) {
        List<OrderItem> orderItems = new ArrayList<>();

        orderDto.getOrderItems().forEach(item -> {
            Product product = this.productRepository.findById(item.getProductId()).orElseThrow(
                    () -> new OrderCreateException("Product not found with id: " + item.getProductId())
            );

            orderItems.add(this.orderMapper.requestToOrderItem(
                    item,
                    order,
                    product
            ));
        });

        order.setOrderItems(orderItems);
    }

}
