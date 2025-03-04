package com.example.shop_java.web.response.category;

import lombok.Data;

import java.util.List;

@Data
public class CategoryListResponse {

    private List<Category> categories;

    @Data
    public static class Category {

        private Long id;

        private String name;

    }

}
