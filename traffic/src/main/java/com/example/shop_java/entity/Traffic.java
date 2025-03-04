package com.example.shop_java.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Traffic {

    @Id
    private String id;

    private TrafficType trafficType;

    private TrafficStatus trafficStatus;

    private Long productId;

    private LocalDateTime createAt;

}
