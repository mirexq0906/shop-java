package com.example.shop_java.service.impl;

import com.example.shop_java.entity.Product;
import com.example.shop_java.exception.EntityNotFoundException;
import com.example.shop_java.message.TrafficMessage;
import com.example.shop_java.repository.ProductRepository;
import com.example.shop_java.repository.specification.ProductSpecifications;
import com.example.shop_java.service.ProductService;
import com.example.shop_java.service.event.TrafficEventService;
import com.example.shop_java.util.ObjectUtils;
import com.example.shop_java.web.dto.product.ProductValidationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final TrafficEventService trafficEventService;

    private final RedisTemplate<String, List<Product>> redisTemplate;

    @Override
    public List<Product> findAll(ProductValidationDto productValidationDto) {
        return productRepository.findAll(
                ProductSpecifications.toSpecifications(productValidationDto),
                PageRequest.of(productValidationDto.getPageNumber(), productValidationDto.getPageSize())
        ).getContent();
    }

    @Override
    public List<Product> findAllByIds(List<Long> ids) {
        return productRepository.findAllById(ids);
    }

    @Override
    public List<Product> getAnalyzeProducts() {
        return redisTemplate.opsForValue().get("popular-view-product");
    }

    @Override
    public Product findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        this.trafficEventService.sendEvent(id, TrafficMessage.TrafficType.SHOW);
        return product;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Product updatedProduct = findById(product.getId());
        ObjectUtils.copyFields(product, updatedProduct);
        return productRepository.save(updatedProduct);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
