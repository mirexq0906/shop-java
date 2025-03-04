package com.example.shop_java.web.controller;

import com.example.shop_java.web.dto.CartDto;
import com.example.shop_java.web.response.cart.CartItemResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CartControllerTest extends AbstractControllerTest {

    @Test
    public void findAllByStoredId_returnCartItems() throws Exception {
        CartDto cartDto1 = new CartDto();
        cartDto1.setProductId(1L);

        CartDto cartDto2 = new CartDto();
        cartDto2.setProductId(2L);

        String response = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartDto1))).andReturn().getResponse().getContentAsString();

        CartItemResponse cartItemResponse = this.objectMapper.readValue(response, CartItemResponse.class);
        cartDto2.setStoredId(cartItemResponse.getStoredId());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartDto2)));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cart/" + cartDto2.getStoredId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[0].length()").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[0].storedId").value(cartDto2.getStoredId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[0].product.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[0].product.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[0].product.name").value("product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[0].product.price").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[0].product.oldPrice").value(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[0].product.image").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[0].quantity").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[1].length()").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[1].storedId").value(cartDto2.getStoredId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[1].product.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[1].product.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[1].product.name").value("product 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[1].product.price").value(102))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[1].product.oldPrice").value(202))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[1].product.image").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartItems[1].quantity").value(1));
    }

    @Test
    public void addProductToCart_returnCartItem() throws Exception {
        CartDto cartDto1 = new CartDto();
        cartDto1.setProductId(1L);

        CartDto cartDto2 = new CartDto();
        cartDto2.setProductId(2L);

        Assertions.assertEquals(0, this.cartRepository.count());
        Assertions.assertEquals(0, this.cartItemRepository.count());

        String response = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(cartDto1)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.storedId").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.name").value("product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.price").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.oldPrice").value(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.image").isString())
                .andReturn().getResponse().getContentAsString();

        CartItemResponse cartItemResponse = this.objectMapper.readValue(response, CartItemResponse.class);
        cartDto2.setStoredId(cartItemResponse.getStoredId());

        Assertions.assertEquals(1, this.cartRepository.count());
        Assertions.assertEquals(1, this.cartItemRepository.count());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(cartDto2)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.storedId").value(cartDto2.getStoredId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.name").value("product 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.price").value(102))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.oldPrice").value(202))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.image").isString());

        Assertions.assertEquals(1, this.cartRepository.count());
        Assertions.assertEquals(2, this.cartItemRepository.count());
    }

    @Test
    public void removeProductFromCart_returnNoContent() throws Exception {
        CartDto cartDto1 = new CartDto();
        cartDto1.setProductId(1L);
        CartDto cartDto2 = new CartDto();
        cartDto2.setProductId(2L);

        String response = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(cartDto1)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        CartItemResponse cartItemResponse = this.objectMapper.readValue(response, CartItemResponse.class);
        cartDto2.setStoredId(cartItemResponse.getStoredId());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(cartDto2)));

        Assertions.assertEquals(1, this.cartRepository.count());
        Assertions.assertEquals(2, this.cartItemRepository.count());

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cart/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(cartDto2)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertEquals(1, this.cartRepository.count());
        Assertions.assertEquals(1, this.cartItemRepository.count());
    }

}
