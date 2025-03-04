package com.example.shop_java.repository;

import com.example.shop_java.entity.Traffic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrafficRepository extends MongoRepository<Traffic, Long> {
}
