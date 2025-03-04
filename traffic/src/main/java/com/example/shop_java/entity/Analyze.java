package com.example.shop_java.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Data
public class Analyze {

    @Id
    private String id;

    private Long viewsCount;

    private Long ordersCount;

    private Long addCartCount;

    private Long productId;

    public void incrementViewsCount() {
        this.viewsCount++;
    }

    public void incrementOrdersCount() {
        this.ordersCount++;
    }

    public void incrementAddCartCount() {
        this.addCartCount++;
    }

}
