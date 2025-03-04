package com.example.shop_java.web.controller;

import com.example.shop_java.web.dto.user.JwtDto;
import com.example.shop_java.web.dto.user.LoginDto;
import com.example.shop_java.web.dto.user.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class UserControllerTest extends AbstractControllerTest {

    @Test
    public void register_returnUser() throws Exception {
        Assertions.assertEquals(1, userRepository.count());

        UserDto userDto = new UserDto();
        userDto.setUsername("test2");
        userDto.setEmail("test@test.com");
        userDto.setPassword("123456");
        userDto.setRole("ROLE_USER");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@test.com"));

        Assertions.assertEquals(2, userRepository.count());
    }

    @Test
    public void login_returnStatusOk_thenCheck_returnSuccess() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("test");
        loginDto.setPassword("12345");

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

        JwtDto jwtDto = objectMapper.readValue(response, JwtDto.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
