package com.example.shop_java.web.mapper;

import com.example.shop_java.entity.Analyze;
import com.example.shop_java.web.dto.AnalyzeDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnalyzeMapper {

    public List<Analyze> requestToAnalyzeList(AnalyzeDto analyzeDto) {
        return analyzeDto.getAnalyzeResponseList().stream()
                .map(item -> {
                    return Analyze.builder()
                            .id(item.getId())
                            .ordersCount(item.getOrdersCount())
                            .addCartCount(item.getAddCartCount())
                            .viewsCount(item.getViewsCount())
                            .productId(item.getProductId())
                            .build();
                })
                .toList();
    }

    public List<Long> requestToProductIdList(AnalyzeDto analyzeDto) {
        return analyzeDto.getAnalyzeResponseList().stream()
                .map(AnalyzeDto.AnalyzeResponse::getProductId)
                .toList();
    }

}
