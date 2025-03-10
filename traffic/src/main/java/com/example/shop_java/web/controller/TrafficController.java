package com.example.shop_java.web.controller;

import com.example.shop_java.service.AnalyzeService;
import com.example.shop_java.service.TrafficAnalyzeService;
import com.example.shop_java.service.TrafficService;
import com.example.shop_java.web.mapper.AnalyzeMapper;
import com.example.shop_java.web.response.AnalyzeListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/traffic")
public class TrafficController {

    private final AnalyzeService analyzeService;

    private final AnalyzeMapper analyzeMapper;

    private final TrafficAnalyzeService trafficAnalyzeService;

    private final TrafficService trafficService;

    @GetMapping
    public ResponseEntity<AnalyzeListResponse> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        this.analyzeMapper.analyzeListToAnalyzeListResponse(
                                this.analyzeService.findAll(pageable)
                        )
                );
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.trafficService.count()
        );
    }

    @PostMapping("/run")
    public ResponseEntity<Void> run() {
        this.trafficAnalyzeService.run();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
