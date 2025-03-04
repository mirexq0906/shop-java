package com.example.shop_java.repository.specification;

import com.example.shop_java.entity.Product;
import com.example.shop_java.web.dto.product.ProductValidationDto;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> toSpecifications(ProductValidationDto productValidationDto) {
        return nameContains(productValidationDto.getName())
                .and(priceBetween(productValidationDto.getMinPrice(), productValidationDto.getMaxPrice()))
                .and(sortByField(productValidationDto.getSortField(), productValidationDto.getSortOrder()));
    }

    public static Specification<Product> nameContains(String name) {
        return (root, query, builder) -> {
            if (name == null) {
                return null;
            }

            return builder.like(root.get(Product.Fields.name), "%" + name + "%");
        };
    }

    public static Specification<Product> priceBetween(Long minPrice, Long maxPrice) {
        return (root, query, builder) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }

            if (minPrice == null) {
                return builder.lessThan(root.get(Product.Fields.price), maxPrice);
            }

            if (maxPrice == null) {
                return builder.greaterThan(root.get(Product.Fields.price), minPrice);
            }

            return builder.between(root.get(Product.Fields.price), minPrice, maxPrice);
        };
    }

    public static Specification<Product> sortByField(String sortField, Integer sortOrder) {
        return (root, query, builder) -> {
            if (sortField == null) {
                return null;
            }

            if (query != null) {
                switch (sortOrder) {
                    case -1 -> query.orderBy(builder.asc(root.get(sortField)));
                    case 1 -> query.orderBy(builder.desc(root.get(sortField)));
                }
            }

            return null;
        };
    }

}
