package com.example.shop_java.web.controller;

import com.example.shop_java.web.dto.CategoryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CategoryControllerTest extends AbstractControllerTest {

    @Test
    public void findAll_returnAllCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories.length()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].name").value("test 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[9].length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[9].name").value("test 10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[9].id").value(10));
    }

    @Test
    public void findById_returnCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test 1"));
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void create_returnCategory() throws Exception {
        Assertions.assertEquals(10, categoryRepository.count());
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("test 11");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test 11"));

        Assertions.assertEquals(11, categoryRepository.count());
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void update_returnCategory() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("test 1 updated");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/category/1")
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test 1 updated"));
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void delete_returnCategory() throws Exception {
        Assertions.assertEquals(10, categoryRepository.count());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/category/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertEquals(9, categoryRepository.count());
    }
}
