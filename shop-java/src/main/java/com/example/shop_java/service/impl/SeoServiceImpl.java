package com.example.shop_java.service.impl;

import com.example.shop_java.entity.Seo;
import com.example.shop_java.exception.EntityNotFoundException;
import com.example.shop_java.repository.SeoRepository;
import com.example.shop_java.service.SeoService;
import com.example.shop_java.util.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeoServiceImpl implements SeoService {

    private final SeoRepository seoRepository;

    @Override
    public List<Seo> findAll(Pageable pageable) {
        return this.seoRepository.findAll(pageable).getContent();
    }

    @Override
    public Seo findById(Long id) {
        return this.seoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Seo not found"));
    }

    @Override
    public Seo findByUri(String uri) {
        return this.seoRepository.findByUri(uri).orElse(null);
    }

    @Override
    public Seo save(Seo seo) {
        return this.seoRepository.save(seo);
    }

    @Override
    public Seo update(Seo seo) {
        Seo updatedSeo = this.findById(seo.getId());
        ObjectUtils.copyFields(seo, updatedSeo);
        return this.seoRepository.save(seo);
    }

    @Override
    public void deleteById(Long id) {
        this.seoRepository.deleteById(id);
    }

}
