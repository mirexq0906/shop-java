package com.example.shop_java.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ProductValidationControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenCreateProductWithInvalidData_thenReturnStatus400() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "image.png", MediaType.IMAGE_JPEG_VALUE, "".getBytes());
        MockMultipartFile gallery1 = new MockMultipartFile("gallery", "gallery-1.png", MediaType.IMAGE_JPEG_VALUE, "".getBytes());
        MockMultipartFile gallery2 = new MockMultipartFile("gallery", "gallery-2.png", MediaType.IMAGE_JPEG_VALUE, "".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/product")
                        .file(image)
                        .file(gallery1)
                        .file(gallery2)
                        .param("name", "1")
                        .param("description", "1")
                        .param("price", "0")
                        .param("oldPrice", "0")
                        .param("categoryId", "0"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.description").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.price").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.oldPrice").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.categoryId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.image").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.gallery").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/product"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.description").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.price").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.oldPrice").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.categoryId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.image").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.gallery").isNotEmpty());
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenUpdateProductWithInvalidData_thenReturnStatus400() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "image.png", MediaType.IMAGE_JPEG_VALUE, "".getBytes());
        MockMultipartFile gallery1 = new MockMultipartFile("gallery", "gallery-1.png", MediaType.IMAGE_JPEG_VALUE, "".getBytes());
        MockMultipartFile gallery2 = new MockMultipartFile("gallery", "gallery-2.png", MediaType.IMAGE_JPEG_VALUE, "".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/product")
                        .file(image)
                        .file(gallery1)
                        .file(gallery2)
                        .param("name", "1")
                        .param("description", "1")
                        .param("price", "0")
                        .param("oldPrice", "0")
                        .param("categoryId", "0"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.description").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.price").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.oldPrice").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.categoryId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.image").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.gallery").isNotEmpty());
    }

}
