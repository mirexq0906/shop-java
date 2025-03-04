package com.example.shop_java.web.mapper;

import com.example.shop_java.entity.Product;
import com.example.shop_java.entity.cart.Cart;
import com.example.shop_java.entity.cart.CartItem;
import com.example.shop_java.web.response.cart.CartItemListResponse;
import com.example.shop_java.web.response.cart.CartItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartMapper {

    public CartItem toCartItem(Product product, Cart cart) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setQuantity(1L);
        return cartItem;
    }

    public CartItemResponse cartItemToCartItemResponse(CartItem cartItem) {
        CartItemResponse cartItemResponse = new CartItemResponse();
        CartItemResponse.Product productResponse = new CartItemResponse.Product();

        productResponse.setId(cartItem.getProduct().getId());
        productResponse.setName(cartItem.getProduct().getName());
        productResponse.setPrice(cartItem.getProduct().getPrice());
        productResponse.setOldPrice(cartItem.getProduct().getOldPrice());
        productResponse.setImage(cartItem.getProduct().getImage());

        cartItemResponse.setId(cartItem.getId());
        cartItemResponse.setStoredId(cartItem.getCart().getStoredId());
        cartItemResponse.setQuantity(cartItem.getQuantity());
        cartItemResponse.setProduct(productResponse);

        return cartItemResponse;
    }

    public CartItemListResponse cartItemListToCartItemListResponse(List<CartItem> cartItemList) {
        CartItemListResponse cartItemListResponse = new CartItemListResponse();
        cartItemListResponse.setCartItems(
                cartItemList.stream().map(this::cartItemToCartItemResponse).toList()
        );
        return cartItemListResponse;
    }

}
