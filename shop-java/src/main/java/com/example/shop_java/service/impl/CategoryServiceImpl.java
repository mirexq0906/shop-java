package com.example.shop_java.service.impl;

import com.example.shop_java.entity.Category;
import com.example.shop_java.exception.EntityNotFoundException;
import com.example.shop_java.repository.CategoryRepository;
import com.example.shop_java.service.CategoryService;
import com.example.shop_java.util.ObjectUtils;
import com.example.shop_java.web.dto.validation.entity_exist.EntityExistService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "category")
public class CategoryServiceImpl implements CategoryService, EntityExistService {

    private final CategoryRepository categoryRepository;

    @Override
    @Cacheable
    public List<Category> findAll(Pageable pageable) {
        return this.categoryRepository.findAll(pageable).getContent();
    }

    @Override
    @Cacheable(key = "#id")
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    @CacheEvict(allEntries = true)
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @CacheEvict(allEntries = true)
    public Category update(Category category) {
        Category updatedCategory = findById(category.getId());
        ObjectUtils.copyFields(category, updatedCategory);
        return categoryRepository.save(updatedCategory);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean checkEntityExistById(Long id) {
        return this.categoryRepository.existsById(id);
    }

}
