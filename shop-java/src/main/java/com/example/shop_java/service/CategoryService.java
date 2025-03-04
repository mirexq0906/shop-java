package com.example.shop_java.service;

import com.example.shop_java.entity.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    List<Category> findAll(Pageable pageable);

    Category findById(Long id);

    Category save(Category category);

    Category update(Category category);

    void deleteById(Long id);

}
