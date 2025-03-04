package com.example.shop_java.web.mapper;

import com.example.shop_java.entity.Category;
import com.example.shop_java.web.dto.CategoryDto;
import com.example.shop_java.web.response.category.CategoryListResponse;
import com.example.shop_java.web.response.category.CategoryResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {

    public CategoryResponse categoryToResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }

    public CategoryListResponse categoryListToResponse(List<Category> categoryList) {
        CategoryListResponse categoryListResponse = new CategoryListResponse();
        List<CategoryListResponse.Category> categories = new ArrayList<>();

        for (Category category : categoryList) {
            CategoryListResponse.Category categoryResponse = new CategoryListResponse.Category();
            categoryResponse.setId(category.getId());
            categoryResponse.setName(category.getName());
            categories.add(categoryResponse);
        }

        categoryListResponse.setCategories(categories);
        return categoryListResponse;
    }

    public Category requestToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }

    public Category requestToCategory(CategoryDto categoryDto, Long id) {
        Category category = this.requestToCategory(categoryDto);
        category.setId(id);
        return category;
    }

}
