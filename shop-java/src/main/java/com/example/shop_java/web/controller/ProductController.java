package com.example.shop_java.web.controller;

import com.example.shop_java.service.ProductService;
import com.example.shop_java.web.dto.product.ProductDto;
import com.example.shop_java.web.dto.product.ProductValidationDto;
import com.example.shop_java.web.dto.validation.OnCreate;
import com.example.shop_java.web.dto.validation.OnUpdate;
import com.example.shop_java.web.mapper.ProductMapper;
import com.example.shop_java.web.response.product.ProductListResponse;
import com.example.shop_java.web.response.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<ProductListResponse> findAll(ProductValidationDto productValidationDto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productMapper.productListToProductListResponse(productService.findAll(productValidationDto))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productMapper.productToProductResponse(productService.findById(id))
        );
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProductResponse> create(@ModelAttribute @Validated(OnCreate.class) ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                productMapper.productToProductResponse(
                        productService.save(
                                productMapper.requestToProduct(productDto)
                        )
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @ModelAttribute @Validated(OnUpdate.class) ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                productMapper.productToProductResponse(
                        productService.update(
                                productMapper.requestToProduct(productDto, id)
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProductResponse> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
