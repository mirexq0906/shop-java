package com.example.shop_java.web.controller;

import com.example.shop_java.web.dto.product.ProductDto;
import com.example.shop_java.web.dto.product.ProductValidationDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ProductFilterControllerTest extends AbstractControllerTest {

    @Test
    public void findAllWithPageFilters_returnFilteredProducts() throws Exception {
        MultiValueMap<String, String> params1 = new LinkedMultiValueMap<>();
        params1.add(ProductValidationDto.Fields.pageNumber, "0");
        params1.add(ProductValidationDto.Fields.pageSize, "5");

        MultiValueMap<String, String> params2 = new LinkedMultiValueMap<>();
        params2.add(ProductValidationDto.Fields.pageNumber, "1");
        params2.add(ProductValidationDto.Fields.pageSize, "3");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product")
                        .params(params1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[4].id").value(5));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product")
                        .params(params2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[2].id").value(6));
    }

    @Test
    public void findAllWithSort_returnSortedProducts() throws Exception {
        MultiValueMap<String, String> params1 = new LinkedMultiValueMap<>();
        params1.add(ProductValidationDto.Fields.sortField, "id");
        params1.add(ProductValidationDto.Fields.sortOrder, "-1");
        params1.add(ProductValidationDto.Fields.pageNumber, "0");
        params1.add(ProductValidationDto.Fields.pageSize, "10");

        MultiValueMap<String, String> params2 = new LinkedMultiValueMap<>();
        params2.add(ProductValidationDto.Fields.sortField, "id");
        params2.add(ProductValidationDto.Fields.sortOrder, "1");
        params2.add(ProductValidationDto.Fields.pageNumber, "0");
        params2.add(ProductValidationDto.Fields.pageSize, "10");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product")
                        .params(params1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products.length()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[9].id").value(10));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product")
                        .params(params2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products.length()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].id").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[9].id").value(1));

    }

    @Test
    public void findAllWithFilters_returnFilteredProducts() throws Exception {
        MultiValueMap<String, String> params1 = new LinkedMultiValueMap<>();
        params1.add(ProductValidationDto.Fields.name, "product 1");

        MultiValueMap<String, String> params2 = new LinkedMultiValueMap<>();
        params2.add(ProductValidationDto.Fields.maxPrice, "103");
        params2.add(ProductValidationDto.Fields.minPrice, "101");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product")
                        .params(params1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].name").value("product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[1].name").value("product 10"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product")
                        .params(params2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].price").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[1].price").value(102))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[2].price").value(103));
    }

}
