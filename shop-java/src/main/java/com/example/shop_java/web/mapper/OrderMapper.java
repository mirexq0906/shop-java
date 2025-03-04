package com.example.shop_java.web.mapper;

import com.example.shop_java.entity.Product;
import com.example.shop_java.entity.order.Order;
import com.example.shop_java.entity.order.OrderCustomer;
import com.example.shop_java.entity.order.OrderItem;
import com.example.shop_java.web.dto.OrderDto;
import com.example.shop_java.web.response.order.OrderResponse;
import com.example.shop_java.web.response.product.ProductListResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderCustomer requestToOrderCustomer(OrderDto.OrderCustomer orderCustomerDto) {
        OrderCustomer orderCustomer = new OrderCustomer();
        orderCustomer.setEmail(orderCustomerDto.getEmail());
        orderCustomer.setFirstName(orderCustomerDto.getFirstName());
        orderCustomer.setLastName(orderCustomerDto.getLastName());
        orderCustomer.setPhone(orderCustomerDto.getPhone());
        orderCustomer.setAddress(orderCustomerDto.getAddress());
        return orderCustomer;
    }

    public OrderItem requestToOrderItem(OrderDto.OrderItem orderItemDto, Order order, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setPrice(product.getPrice());
        orderItem.setQuantity(orderItemDto.getQuantity());
        return orderItem;
    }

    public OrderResponse orderToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        OrderResponse.OrderCustomer orderCustomer = new OrderResponse.OrderCustomer();

        orderResponse.setId(order.getId());
        orderResponse.setDeliveryType(order.getDeliveryType().getName());
        orderResponse.setPaymentMethod(order.getPaymentMethod().getName());
        orderResponse.setOrderItems(order.getOrderItems().stream().map(this::orderItemToOrderResponse).toList());
        orderResponse.setAmount(order.getAmount());

        orderCustomer.setEmail(order.getCustomer().getEmail());
        orderCustomer.setFirstName(order.getCustomer().getFirstName());
        orderCustomer.setLastName(order.getCustomer().getLastName());
        orderCustomer.setPhone(order.getCustomer().getPhone());
        orderCustomer.setAddress(order.getCustomer().getAddress());
        orderResponse.setCustomer(orderCustomer);

        return orderResponse;
    }

    private OrderResponse.OrderItem orderItemToOrderResponse(OrderItem orderItem) {
        OrderResponse.OrderItem orderItemResponse = new OrderResponse.OrderItem();
        ProductListResponse.Product productResponse = new ProductListResponse.Product();

        productResponse.setId(orderItem.getProduct().getId());
        productResponse.setName(orderItem.getProduct().getName());
        productResponse.setPrice(orderItem.getProduct().getPrice());
        productResponse.setOldPrice(orderItem.getProduct().getOldPrice());
        productResponse.setImage(orderItem.getProduct().getImage());

        orderItemResponse.setQuantity(orderItem.getQuantity());
        orderItemResponse.setProduct(productResponse);

        return orderItemResponse;
    }

}
