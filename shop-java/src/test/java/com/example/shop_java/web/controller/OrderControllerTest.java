package com.example.shop_java.web.controller;

import com.example.shop_java.web.dto.CartDto;
import com.example.shop_java.web.dto.OrderDto;
import com.example.shop_java.web.response.cart.CartItemResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

public class OrderControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void findById_andReturnOrder() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryType").value("delivery"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentMethod").value("payment"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].product.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].product.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].product.name").value("product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].product.price").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].product.oldPrice").value(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].product.image").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.firstName").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.lastName").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.email").value("test@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.phone").value("79999999999"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.address").value("test address"));
    }

    @Test
    @WithUserDetails(value = "test", userDetailsServiceBeanName = "userServiceImpl", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void createOrder_andReturnOrder() throws Exception {
        CartDto cartDto1 = new CartDto();
        cartDto1.setProductId(1L);

        CartDto cartDto2 = new CartDto();
        cartDto2.setProductId(2L);

        OrderDto orderDto = new OrderDto();
        OrderDto.OrderCustomer customer = new OrderDto.OrderCustomer();
        OrderDto.OrderItem orderItem1 = new OrderDto.OrderItem();
        OrderDto.OrderItem orderItem2 = new OrderDto.OrderItem();

        customer.setFirstName("test");
        customer.setLastName("test");
        customer.setEmail("test@example.com");
        customer.setPhone("7999999999");
        customer.setAddress("test address");

        orderItem1.setProductId(cartDto1.getProductId());
        orderItem1.setQuantity(1);
        orderItem2.setProductId(cartDto2.getProductId());
        orderItem2.setQuantity(1);

        orderDto.setDeliveryTypeId(1L);
        orderDto.setPaymentMethodId(1L);
        orderDto.setOrderCustomer(customer);
        orderDto.setOrderItems(List.of(orderItem1, orderItem2));


        String response = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartDto1))).andReturn().getResponse().getContentAsString();

        CartItemResponse cartItemResponse = this.objectMapper.readValue(response, CartItemResponse.class);
        cartDto2.setStoredId(cartItemResponse.getStoredId());
        orderDto.setStoredId(cartItemResponse.getStoredId());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartDto2)));

        Assertions.assertEquals(1, this.cartRepository.count());
        Assertions.assertEquals(1, this.orderRepository.count());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryType").value("delivery"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentMethod").value("payment"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].product.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].product.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].product.name").value("product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].product.price").value(101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].product.oldPrice").value(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].product.image").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.length()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.firstName").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.lastName").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.email").value("test@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.phone").value("7999999999"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.address").value("test address"));

        Assertions.assertEquals(0, this.cartRepository.count());
        Assertions.assertEquals(2, this.orderRepository.count());
    }

}
