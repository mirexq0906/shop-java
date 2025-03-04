package com.example.shop_java.web.response.product;

import com.example.shop_java.entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class ProductListResponse {

    private List<Product> products;

    @Data
    public static class Product {

        private Long id;

        private String name;

        private Long price;

        private Long oldPrice;

        private String image;

    }
}
