package com.example.shop_java.web.response.product;

import com.example.shop_java.web.response.category.CategoryResponse;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private Long price;

    private Long oldPrice;

    private String image;

    private List<String> gallery;

    private CategoryResponse category;

}
