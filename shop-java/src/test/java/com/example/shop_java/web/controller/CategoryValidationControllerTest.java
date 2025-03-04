package com.example.shop_java.web.controller;

import com.example.shop_java.web.dto.CategoryDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CategoryValidationControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenCreateCategoryWithInvalidData_thenReturnStatus400() throws Exception {
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setName("1");

        CategoryDto categoryDto2 = new CategoryDto();
        categoryDto1.setName(null);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto1)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.name").isNotEmpty());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto2)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.name").isNotEmpty());
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenUpdateCategoryWithValidData_thenReturnStatus400() throws Exception {
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setName("1");

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto1)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.name").isNotEmpty());

    }

}
