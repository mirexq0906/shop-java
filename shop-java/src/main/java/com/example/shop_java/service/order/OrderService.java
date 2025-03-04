package com.example.shop_java.service.order;

import com.example.shop_java.entity.order.Order;
import com.example.shop_java.web.dto.OrderDto;

public interface OrderService {

    Order findByIdAndUsername(Long id);

    Order save(OrderDto orderDto);

}
