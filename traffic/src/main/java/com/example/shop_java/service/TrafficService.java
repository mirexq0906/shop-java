package com.example.shop_java.service;

import com.example.shop_java.entity.Traffic;

import java.util.List;

public interface TrafficService {

    List<Traffic> findAll(int page, int size);

    void batchSave(List<Traffic> traffics);

    void deleteAll();

    Long count();

}
