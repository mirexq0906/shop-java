package com.example.shop_java.scheduler;

import com.example.shop_java.entity.Analyze;
import com.example.shop_java.entity.Product;
import com.example.shop_java.service.ProductService;
import com.example.shop_java.web.client.AnalyzeProductClient;
import com.example.shop_java.web.mapper.AnalyzeMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyzeProductScheduled implements Runnable {

    private final AnalyzeProductClient analyzeProductClient;

    private final RedisTemplate<String, List<Product>> redisTemplate;

    private final AnalyzeMapper analyzeMapper;

    private final ProductService productService;

    @SneakyThrows
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.HOURS)
    @Override
    public void run() {
        List<Long> analyzeProductIdList = analyzeMapper.requestToProductIdList(
                this.analyzeProductClient.getAnalyzeProduct()
        );

        List<Product> productList = productService.findAllByIds(analyzeProductIdList);

        redisTemplate.opsForValue().set("popular-view-product", productList);
    }

}
