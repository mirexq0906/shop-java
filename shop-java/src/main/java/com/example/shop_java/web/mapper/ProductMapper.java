package com.example.shop_java.web.mapper;

import com.example.shop_java.entity.Product;
import com.example.shop_java.service.CategoryService;
import com.example.shop_java.service.FileStorageService;
import com.example.shop_java.web.dto.product.ProductDto;
import com.example.shop_java.web.response.category.CategoryResponse;
import com.example.shop_java.web.response.product.ProductListResponse;
import com.example.shop_java.web.response.product.ProductResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final FileStorageService fileStorageService;

    private final CategoryService categoryService;

    private final ObjectMapper objectMapper;

    public ProductResponse productToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId(product.getCategory().getId());
        categoryResponse.setName(product.getCategory().getName());

        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setOldPrice(product.getOldPrice());
        productResponse.setImage(product.getImage());
        try {
            productResponse.setGallery(
                    objectMapper.readValue(product.getGallery(), List.class)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        productResponse.setCategory(categoryResponse);

        return productResponse;
    }

    public ProductListResponse productListToProductListResponse(List<Product> productList) {
        ProductListResponse productListResponse = new ProductListResponse();
        List<ProductListResponse.Product> products = new ArrayList<>();
        for (Product product : productList) {
            ProductListResponse.Product productResponse = new ProductListResponse.Product();
            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setPrice(product.getPrice());
            productResponse.setOldPrice(product.getOldPrice());
            productResponse.setImage(product.getImage());
            products.add(productResponse);
        }
        productListResponse.setProducts(products);
        return productListResponse;
    }

    public Product requestToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setOldPrice(productDto.getOldPrice());

        if (productDto.getImage() != null) {
            product.setImage(fileStorageService.storeFile(productDto.getImage()));
        }

        if (productDto.getGallery() != null) {
            try {
                product.setGallery(
                        objectMapper.writeValueAsString(
                                fileStorageService.storeFiles(productDto.getGallery())
                        )
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        if (productDto.getCategoryId() != null) {
            product.setCategory(categoryService.findById(productDto.getCategoryId()));
        }

        return product;
    }

    public Product requestToProduct(ProductDto productDto, Long productId) {
        Product product = requestToProduct(productDto);
        product.setId(productId);
        return product;
    }

}
