package com.example.shop_java.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnalyzeDto {

    private List<AnalyzeResponse> analyzeResponseList;

    @Data
    public static class AnalyzeResponse {

        private String id;

        private Long viewsCount;

        private Long ordersCount;

        private Long addCartCount;

        private Long productId;

    }

}
