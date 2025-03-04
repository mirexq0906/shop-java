package com.example.shop_java.service;

import com.example.shop_java.entity.Analyze;

import java.util.List;

public interface AnalyzeService {

    List<Analyze> findAll();

    void deleteAll();

    void batchSave(List<Analyze> products);

}
