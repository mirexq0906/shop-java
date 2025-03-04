package com.example.shop_java.repository;

import com.example.shop_java.entity.Analyze;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnalyzeRepository extends MongoRepository<Analyze, String> {
}
