package com.example.shop_java.message;

import com.example.shop_java.entity.TrafficType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrafficMessage {

    private String id;

    private TrafficType trafficType;

    private Long productId;

    private LocalDateTime createdAt;

}
