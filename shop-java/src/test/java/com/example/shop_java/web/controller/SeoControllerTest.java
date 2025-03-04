package com.example.shop_java.web.controller;

import com.example.shop_java.web.dto.SeoDto;
import com.example.shop_java.web.interception.SeoInterception;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class SeoControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void findAll_returnsAllSeo() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/seo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList.length()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList[0].length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList[0].uri").value("uri/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList[0].title").value("title 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList[0].description").value("description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList[0].keywords").value("keywords 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList[9].length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList[9].id").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList[9].uri").value("uri/10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList[9].title").value("title 10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList[9].description").value("description 10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seoList[9].keywords").value("keywords 10"));
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void findById_returnsSeo() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/seo/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uri").value("uri/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.keywords").value("keywords 1"));
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void create_returnsSeo() throws Exception {
        SeoDto seoDto = new SeoDto();
        seoDto.setUri("uri/11");
        seoDto.setTitle("title 11");
        seoDto.setDescription("description 11");
        seoDto.setKeywords("keywords 11");

        Assertions.assertEquals(10, this.seoRepository.count());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/seo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seoDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uri").value("uri/11"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title 11"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description 11"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.keywords").value("keywords 11"));

        Assertions.assertEquals(11, this.seoRepository.count());
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void update_returnsSeo() throws Exception {
        SeoDto seoDto = new SeoDto();
        seoDto.setUri("uri/1/updated");
        seoDto.setTitle("title 1 updated");
        seoDto.setDescription("description 1 updated");
        seoDto.setKeywords("keywords 1 updated");

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/seo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seoDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uri").value("uri/1/updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title 1 updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description 1 updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.keywords").value("keywords 1 updated"));
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void delete_returnVoid() throws Exception {
        Assertions.assertEquals(10, this.seoRepository.count());

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/seo/1"));

        Assertions.assertEquals(9, this.seoRepository.count());
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void findProduct_returnSeoHeaders() throws Exception {
        SeoDto seoDto = new SeoDto();
        seoDto.setUri("/api/v1/product/1");
        seoDto.setTitle("title 1");
        seoDto.setDescription("description 1");
        seoDto.setKeywords("keywords 1");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/seo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seoDto)));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/1"))
                .andExpect(MockMvcResultMatchers.header().string(SeoInterception.SEO_TITLE, "title 1"))
                .andExpect(MockMvcResultMatchers.header().string(SeoInterception.SEO_KEYWORD, "keywords 1"))
                .andExpect(MockMvcResultMatchers.header().string(SeoInterception.SEO_DESCRIPTION, "description 1"));
    }

}
