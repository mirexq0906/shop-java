package com.example.shop_java.web.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ProductControllerTest extends AbstractControllerTest {

    @Test
    public void findAll_returnAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products.length()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].name").value("product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].price").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].oldPrice").value(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].image").value("image1.png"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[9].length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[9].id").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[9].name").value("product 10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[9].price").value(110))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[9].oldPrice").value(210))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[9].image").value("image10.png"));
    }

    @Test
    public void findById_returnProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.oldPrice").value(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("image1.png"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gallery[0]").value("gallery1.png"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").isString());

    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void create_returnProduct() throws Exception {
        Assertions.assertEquals(10, productRepository.count());

        MockMultipartFile image = new MockMultipartFile("image", "image.png", MediaType.IMAGE_JPEG_VALUE, "image-content".getBytes());
        MockMultipartFile gallery1 = new MockMultipartFile("gallery", "gallery-1.png", MediaType.IMAGE_JPEG_VALUE, "gallery-content1".getBytes());
        MockMultipartFile gallery2 = new MockMultipartFile("gallery", "gallery-2.png", MediaType.IMAGE_JPEG_VALUE, "gallery-content2".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/product")
                        .file(image)
                        .file(gallery1)
                        .file(gallery2)
                        .param("name", "product 11")
                        .param("description", "description 11")
                        .param("price", String.valueOf(500))
                        .param("oldPrice", String.valueOf(600))
                        .param("categoryId", "1"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("product 11"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description 11"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.oldPrice").value(600))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.gallery[0]").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.gallery[1]").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").value("test 1"));

        Assertions.assertEquals(11, productRepository.count());
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void update_returnProduct() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "image.png", MediaType.IMAGE_JPEG_VALUE, "image-content".getBytes());
        MockMultipartFile gallery1 = new MockMultipartFile("gallery", "gallery-1.png", MediaType.IMAGE_JPEG_VALUE, "gallery-content1".getBytes());
        MockMultipartFile gallery2 = new MockMultipartFile("gallery", "gallery-2.png", MediaType.IMAGE_JPEG_VALUE, "gallery-content2".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/product/1")
                        .file(image)
                        .file(gallery1)
                        .file(gallery2)
                        .param("name", "product 1 updated")
                        .param("description", "description 1 updated")
                        .param("price", String.valueOf(500))
                        .param("oldPrice", String.valueOf(600))
                        .param("categoryId", "2")
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("product 1 updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description 1 updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.oldPrice").value(600))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.gallery[0]").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.gallery[1]").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").value("test 2"));
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void updatePart_returnProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/product/1")
                        .param("name", "product 1 updated")
                        .param("description", "description 1 updated")
                        .param("price", String.valueOf(500))
                        .param("categoryId", "2")
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("product 1 updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description 1 updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.gallery[0]").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").value("test 2"));
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void delete_returnProduct() throws Exception {
        Assertions.assertEquals(10, productRepository.count());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/product/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertEquals(9, productRepository.count());
    }

}
