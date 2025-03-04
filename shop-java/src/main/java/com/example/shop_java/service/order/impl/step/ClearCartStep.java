package com.example.shop_java.service.order.impl.step;

import com.example.shop_java.entity.cart.Cart;
import com.example.shop_java.entity.order.Order;
import com.example.shop_java.exception.OrderCreateException;
import com.example.shop_java.repository.CartRepository;
import com.example.shop_java.service.order.OrderProcessingStep;
import com.example.shop_java.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClearCartStep implements OrderProcessingStep {

    private final CartRepository cartRepository;

    @Override
    public void process(Order order, OrderDto orderDto) {
        Cart cart = this.cartRepository.findByStoredId(orderDto.getStoredId()).orElseThrow(
                () -> new OrderCreateException("storedId not found in cart")
        );

        this.cartRepository.delete(cart);
    }

}
