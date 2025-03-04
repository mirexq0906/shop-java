package com.example.shop_java.service.impl;

import com.example.shop_java.entity.Product;
import com.example.shop_java.entity.cart.Cart;
import com.example.shop_java.entity.cart.CartItem;
import com.example.shop_java.exception.EntityNotFoundException;
import com.example.shop_java.message.TrafficMessage;
import com.example.shop_java.repository.CartItemRepository;
import com.example.shop_java.repository.CartRepository;
import com.example.shop_java.service.CartService;
import com.example.shop_java.service.ProductService;
import com.example.shop_java.service.event.TrafficEventService;
import com.example.shop_java.web.dto.CartDto;
import com.example.shop_java.web.mapper.CartMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartMapper cartMapper;
    private final TrafficEventService trafficEventService;

    public List<CartItem> findAllCartItems(String storedId) {
        Cart cart = this.findCartByStoredId(storedId);
        return cart.getCartItems();
    }

    private Cart findCartByStoredId(String storedId) {
        return this.cartRepository.findByStoredId(storedId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    }

    private Cart findOrCreateCart(String storedId) {
        Cart cart = this.cartRepository.findByStoredId(storedId).orElse(null);

        if (cart == null) {
            cart = new Cart();
            cart.setStoredId(UUID.randomUUID().toString());
            this.cartRepository.save(cart);
        }

        return cart;
    }

    @Transactional
    @Override
    public CartItem add(CartDto cartDto) {
        Cart cart = this.findOrCreateCart(cartDto.getStoredId());

        if (this.cartItemRepository.findByProduct_IdAndCart_Id(cartDto.getProductId(), cart.getId()).isPresent()) {
            throw new IllegalArgumentException("Product already added to the cart");
        }

        CartItem cartItem = this.cartMapper.toCartItem(this.productService.findById(cartDto.getProductId()), cart);
        CartItem savedCartItem = this.cartItemRepository.save(cartItem);
        this.trafficEventService.sendEvent(savedCartItem.getProduct().getId(), TrafficMessage.TrafficType.ADD_CART);

        return savedCartItem;
    }

    @Transactional
    @Override
    public void remove(CartDto cartDto) {
        Cart cart = this.findCartByStoredId(cartDto.getStoredId());

        if (!this.cartItemRepository.existsByProduct_IdAndCart_Id(cartDto.getProductId(), cart.getId())) {
            throw new EntityNotFoundException("CartItem not found");
        }

        this.cartItemRepository.deleteByProduct_IdAndCart_Id(cartDto.getProductId(), cart.getId());
    }

}
