package com.example.shop_java.web.controller;

import com.example.shop_java.web.dto.user.JwtDto;
import com.example.shop_java.web.dto.user.LoginDto;
import com.example.shop_java.web.dto.user.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class UserValidationControllerTest extends AbstractControllerTest {

    @Test
    public void whenRegisterUserWithInvalidData_thenReturn400() throws Exception {
        UserDto userDto1 = new UserDto();
        userDto1.setRole("WRONG");
        userDto1.setUsername("1");
        userDto1.setEmail("wrong");
        userDto1.setPassword("12345");

        UserDto userDto2 = new UserDto();
        userDto2.setRole(null);
        userDto2.setUsername(null);
        userDto2.setEmail(null);
        userDto2.setPassword(null);

        UserDto userDto3 = new UserDto();
        userDto3.setRole("USER");
        userDto3.setUsername("test");
        userDto3.setEmail("test@mail.com");
        userDto3.setPassword("123456");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto1)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.username").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.password").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.email").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.role").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto2)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.username").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.password").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.email").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.role").isNotEmpty());


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto3)))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.username").isNotEmpty());
    }

    @Test
    public void whenLoginUserWithInvalidData_thenReturn500() throws Exception {
        LoginDto loginDto1 = new LoginDto();
        loginDto1.setUsername("wrong");
        loginDto1.setPassword("12345");

        LoginDto loginDto2 = new LoginDto();
        loginDto2.setUsername("test");
        loginDto2.setPassword("123456");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto1)))
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto2)))
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isNotEmpty());
    }

    @Test
    public void whenCheckUserWithInvalidData_thenReturn400() throws Exception {
        JwtDto jwtDto = new JwtDto();
        jwtDto.setToken("token");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtDto)))
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isNotEmpty());
    }
}
