package com.example.shop_java.web.mapper;

import com.example.shop_java.entity.Analyze;
import com.example.shop_java.web.response.AnalyzeListResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnalyzeMapper {

    public AnalyzeListResponse analyzeListToAnalyzeListResponse(List<Analyze> analyzeList) {
        AnalyzeListResponse analyzeListResponse = new AnalyzeListResponse();
        analyzeListResponse.setAnalyzeResponseList(
                analyzeList.stream().map(this::analyzeToAnalyzeResponse).toList()
        );
        return analyzeListResponse;
    }

    private AnalyzeListResponse.AnalyzeResponse analyzeToAnalyzeResponse(Analyze analyze) {
        AnalyzeListResponse.AnalyzeResponse analyzeResponse = new AnalyzeListResponse.AnalyzeResponse();
        analyzeResponse.setId(analyze.getId());
        analyzeResponse.setViewsCount(analyze.getViewsCount());
        analyzeResponse.setAddCartCount(analyze.getAddCartCount());
        analyzeResponse.setOrdersCount(analyze.getOrdersCount());
        analyzeResponse.setProductId(analyze.getProductId());
        return analyzeResponse;
    }

}
