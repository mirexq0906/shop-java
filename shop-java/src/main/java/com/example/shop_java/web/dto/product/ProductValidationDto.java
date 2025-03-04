package com.example.shop_java.web.dto.product;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.Map;

@Data
@FieldNameConstants
public class ProductValidationDto {

    private Integer pageNumber = 0;

    private Integer pageSize = 20;

    private String name;

    private Long minPrice;

    private Long maxPrice;

    private String sortField;

    private Integer sortOrder;

}
