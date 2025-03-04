package com.example.shop_java.service;

import com.example.shop_java.entity.Analyze;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnalyzeService {

    List<Analyze> findAll(Pageable pageable);

    void deleteAll();

    void batchSave(List<Analyze> products);

}
