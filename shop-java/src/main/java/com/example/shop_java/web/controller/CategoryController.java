package com.example.shop_java.web.controller;

import com.example.shop_java.service.CategoryService;
import com.example.shop_java.web.dto.CategoryDto;
import com.example.shop_java.web.dto.validation.OnCreate;
import com.example.shop_java.web.dto.validation.OnUpdate;
import com.example.shop_java.web.mapper.CategoryMapper;
import com.example.shop_java.web.response.category.CategoryListResponse;
import com.example.shop_java.web.response.category.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    private final RabbitTemplate rabbitTemplate;

    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryMapper.categoryListToResponse(categoryService.findAll(pageable))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryMapper.categoryToResponse(categoryService.findById(id))
        );
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CategoryResponse> create(@RequestBody @Validated(OnCreate.class) CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                categoryMapper.categoryToResponse(
                        categoryService.save(
                                categoryMapper.requestToCategory(categoryDto)
                        )
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryMapper.categoryToResponse(
                        categoryService.update(
                                categoryMapper.requestToCategory(categoryDto, id)
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/test")
    public ResponseEntity<Void> test() {
        this.rabbitTemplate.convertAndSend("traffic.queue", "test");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
