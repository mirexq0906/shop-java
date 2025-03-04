package com.example.shop_java.message;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TrafficMessage {

    private String id;

    private TrafficType trafficType;

    private Long productId;

    private LocalDateTime createdAt;

    public enum TrafficType {
        SHOW,
        ORDER,
        ADD_CART
    }

}
