package com.example.shop_java.web.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private Long deliveryTypeId;

    private Long paymentMethodId;

    private OrderCustomer orderCustomer;

    private List<OrderItem> orderItems;

    private String storedId;

    @Data
    public static class OrderCustomer {

        private String firstName;

        private String lastName;

        private String email;

        private String phone;

        private String address;

    }

    @Data
    public static class OrderItem {

        private Long productId;

        private Integer quantity;

    }

}
