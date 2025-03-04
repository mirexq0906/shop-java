package com.example.shop_java.web.controller;

import com.example.shop_java.entity.Analyze;
import com.example.shop_java.service.AnalyzeService;
import com.example.shop_java.service.TrafficAnalyzeService;
import com.example.shop_java.service.TrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/traffic")
public class TrafficController {

    private final TrafficService trafficService;

    private final TrafficAnalyzeService trafficAnalyzeService;

    private final AnalyzeService analyzeService;

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.trafficService.count()
        );
    }

    @GetMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        this.trafficService.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/run")
    public ResponseEntity<Void> run() {
        this.trafficAnalyzeService.run();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<Analyze>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.analyzeService.findAll()
        );
    }

}
