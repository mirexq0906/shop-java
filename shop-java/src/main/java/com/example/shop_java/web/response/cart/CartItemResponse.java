package com.example.shop_java.web.response.cart;

import lombok.Data;

@Data
public class CartItemResponse {

    private Long id;

    private String storedId;

    private Product product;

    private Long quantity;

    @Data
    public static class Product {

        private Long id;

        private String name;

        private Long price;

        private Long oldPrice;

        private String image;

    }

}
