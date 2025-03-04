package com.example.shop_java.web.response.order;

import com.example.shop_java.web.response.product.ProductListResponse;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {

    private Long id;

    private String deliveryType;

    private String paymentMethod;

    private List<OrderItem> orderItems;

    private OrderCustomer customer;

    private Long amount;

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

        private ProductListResponse.Product product;

        private Integer quantity;

    }

}
