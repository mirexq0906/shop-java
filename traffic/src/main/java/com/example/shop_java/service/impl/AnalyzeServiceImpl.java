package com.example.shop_java.service.impl;

import com.example.shop_java.entity.Analyze;
import com.example.shop_java.repository.AnalyzeRepository;
import com.example.shop_java.service.AnalyzeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyzeServiceImpl implements AnalyzeService {

    private final AnalyzeRepository analyzeRepository;

    @Override
    public List<Analyze> findAll() {
        return this.analyzeRepository.findAll();
    }

    @Override
    public void deleteAll() {
        this.analyzeRepository.deleteAll();
    }

    @Override
    public void batchSave(List<Analyze> analyzes) {
        this.analyzeRepository.saveAll(analyzes);
    }

}
