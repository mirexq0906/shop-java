package com.example.shop_java.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Analyze {

    private String id;

    private Long viewsCount;

    private Long ordersCount;

    private Long addCartCount;

    private Long productId;

}
