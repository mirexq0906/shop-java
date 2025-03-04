package com.example.shop_java.web.mapper;

import com.example.shop_java.entity.Seo;
import com.example.shop_java.web.dto.SeoDto;
import com.example.shop_java.web.response.seo.SeoListResponse;
import com.example.shop_java.web.response.seo.SeoResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeoMapper {

    public Seo requestToSeo(SeoDto seoDto) {
        Seo seo = new Seo();
        seo.setUri(seoDto.getUri());
        seo.setDescription(seoDto.getDescription());
        seo.setTitle(seoDto.getTitle());
        seo.setKeywords(seoDto.getKeywords());
        return seo;
    }

    public Seo requestToSeo(SeoDto seoDto, Long id) {
        Seo seo = requestToSeo(seoDto);
        seo.setId(id);
        return seo;
    }

    public SeoResponse seoToResponse(Seo seo) {
        SeoResponse seoResponse = new SeoResponse();
        seoResponse.setId(seo.getId());
        seoResponse.setTitle(seo.getTitle());
        seoResponse.setUri(seo.getUri());
        seoResponse.setDescription(seo.getDescription());
        seoResponse.setKeywords(seo.getKeywords());
        return seoResponse;
    }

    public SeoListResponse seoListToSeoListResponse(List<Seo> seoList) {
        SeoListResponse seoListResponse = new SeoListResponse();
        List<SeoResponse> seoResponseList = seoList.stream().map(this::seoToResponse).toList();
        seoListResponse.setSeoList(seoResponseList);
        return seoListResponse;
    }

}
