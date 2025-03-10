package com.example.shop_java.service;

import com.example.shop_java.entity.Product;
import com.example.shop_java.web.dto.product.ProductValidationDto;

import java.util.List;

public interface ProductService {

    List<Product> findAll(ProductValidationDto productValidationDto);

    List<Product> findAllByIds(List<Long> ids);

    List<Product> getAnalyzeProducts();

    Product findById(Long id);

    Product save(Product product);

    Product update(Product product);

    void deleteById(Long id);

}
