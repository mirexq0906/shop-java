package com.example.shop_java.service.impl;

import com.example.shop_java.entity.Traffic;
import com.example.shop_java.repository.TrafficRepository;
import com.example.shop_java.service.TrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService {

    private final TrafficRepository trafficRepository;

    @Override
    public List<Traffic> findAll(int page, int size) {
        return this.trafficRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    public void batchSave(List<Traffic> traffics) {
        this.trafficRepository.saveAll(traffics);
    }

    @Override
    public void deleteAll() {
        this.trafficRepository.deleteAll();
    }

    @Override
    public Long count() {
        return this.trafficRepository.count();
    }

}
