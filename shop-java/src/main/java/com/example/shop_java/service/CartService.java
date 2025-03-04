package com.example.shop_java.service;

import com.example.shop_java.entity.cart.Cart;
import com.example.shop_java.entity.cart.CartItem;
import com.example.shop_java.web.dto.CartDto;

import java.util.List;

public interface CartService {

    List<CartItem> findAllCartItems(String storedId);

    CartItem add(CartDto cartDto);

    void remove(CartDto cartDto);

}
