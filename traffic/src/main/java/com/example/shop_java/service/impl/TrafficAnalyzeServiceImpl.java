package com.example.shop_java.service.impl;

import com.example.shop_java.entity.Analyze;
import com.example.shop_java.entity.Traffic;
import com.example.shop_java.service.AnalyzeService;
import com.example.shop_java.service.TrafficAnalyzeService;
import com.example.shop_java.service.TrafficService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrafficAnalyzeServiceImpl implements TrafficAnalyzeService {

    private final TrafficService trafficService;

    private final AnalyzeService analyzeService;

    private final int MAX_LIMIT_RECORD = 10000;

    public void run() {
        this.analyzeService.deleteAll();
        int countPage = 0;
        Map<Long, Analyze> trafficMap = new HashMap<>();
        while (true) {
            List<Traffic> trafficList = this.trafficService.findAll(countPage, MAX_LIMIT_RECORD);

            if (trafficList.isEmpty()) {
                break;
            }

            this.analyzeTraffic(trafficMap, trafficList);

            countPage++;
        }

        this.analyzeService.batchSave(new ArrayList<>(trafficMap.values()));
    }

    private void analyzeTraffic(Map<Long, Analyze> analyzes, List<Traffic> traffic) {
        traffic.forEach(item -> {
            analyzes.putIfAbsent(
                    item.getProductId(),
                    Analyze.builder()
                            .id(UUID.randomUUID().toString())
                            .productId(item.getProductId())
                            .viewsCount(0L)
                            .addCartCount(0L)
                            .ordersCount(0L)
                            .build()
            );
            Analyze analyze = analyzes.get(item.getProductId());
            switch (item.getTrafficType()) {
                case ORDER -> analyze.incrementOrdersCount();
                case ADD_CART -> analyze.incrementAddCartCount();
                case SHOW -> analyze.incrementViewsCount();
            }
        });
    }

}
