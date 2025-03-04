package com.example.shop_java.scheduler;

import com.example.shop_java.web.client.AnalyzeProductClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AnalyzeProductScheduled implements Runnable {

    private final AnalyzeProductClient analyzeProductClient;

    @SneakyThrows
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    @Override
    public void run() {
        System.out.println(this.analyzeProductClient.getAnalyzeProduct());
    }

}
