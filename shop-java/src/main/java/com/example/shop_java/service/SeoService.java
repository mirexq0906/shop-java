package com.example.shop_java.service;

import com.example.shop_java.entity.Seo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SeoService {

    List<Seo> findAll(Pageable pageable);

    Seo findById(Long id);

    Seo findByUri(String uri);

    Seo save(Seo seo);

    Seo update(Seo seo);

    void deleteById(Long id);

}
