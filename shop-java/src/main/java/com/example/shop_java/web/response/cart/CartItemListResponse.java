package com.example.shop_java.web.response.cart;

import lombok.Data;

import java.util.List;

@Data
public class CartItemListResponse {

    private List<CartItemResponse> cartItems;

}
