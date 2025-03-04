package com.example.shop_java.service.order.impl.step;

import com.example.shop_java.entity.order.DeliveryType;
import com.example.shop_java.entity.order.Order;
import com.example.shop_java.entity.order.PaymentMethod;
import com.example.shop_java.exception.OrderCreateException;
import com.example.shop_java.repository.order.DeliveryTypeRepository;
import com.example.shop_java.repository.order.OrderRepository;
import com.example.shop_java.repository.order.PaymentMethodRepository;
import com.example.shop_java.service.order.OrderProcessingStep;
import com.example.shop_java.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckOrderDetailsAndSaveOrderStep implements OrderProcessingStep {

    private final DeliveryTypeRepository deliveryTypeRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    private final OrderRepository orderRepository;

    @Override
    public void process(Order order, OrderDto orderDto) {
        DeliveryType deliveryType = this.deliveryTypeRepository.findById(orderDto.getDeliveryTypeId()).orElseThrow(
                () -> new OrderCreateException("delivery type not found")
        );
        PaymentMethod paymentMethod = this.paymentMethodRepository.findById(orderDto.getPaymentMethodId()).orElseThrow(
                () -> new OrderCreateException("payment method not found")
        );

        order.setDeliveryType(deliveryType);
        order.setPaymentMethod(paymentMethod);

        this.orderRepository.save(order);
    }

}
