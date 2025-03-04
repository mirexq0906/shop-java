package com.example.shop_java.web.controller;

import com.example.shop_java.service.CartService;
import com.example.shop_java.web.dto.CartDto;
import com.example.shop_java.web.mapper.CartMapper;
import com.example.shop_java.web.response.cart.CartItemListResponse;
import com.example.shop_java.web.response.cart.CartItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final CartMapper cartMapper;

    @GetMapping("/{storedId}")
    public ResponseEntity<CartItemListResponse> findAll(@PathVariable String storedId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.cartMapper.cartItemListToCartItemListResponse(
                        this.cartService.findAllCartItems(storedId)
                )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemResponse> add(@RequestBody CartDto cartDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.cartMapper.cartItemToCartItemResponse(this.cartService.add(cartDto))
        );
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody CartDto cartDto) {
        this.cartService.remove(cartDto);
        return ResponseEntity.noContent().build();
    }

}
