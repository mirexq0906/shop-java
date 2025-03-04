package com.example.shop_java.web.controller;

import com.example.shop_java.service.order.OrderService;
import com.example.shop_java.web.dto.OrderDto;
import com.example.shop_java.web.mapper.OrderMapper;
import com.example.shop_java.web.response.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.orderMapper.orderToOrderResponse(this.orderService.findByIdAndUsername(id))
        );
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponse> create(@RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                this.orderMapper.orderToOrderResponse(
                        this.orderService.save(orderDto)
                )
        );
    }

}
