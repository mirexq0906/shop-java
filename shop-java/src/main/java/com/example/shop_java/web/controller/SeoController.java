package com.example.shop_java.web.controller;

import com.example.shop_java.service.SeoService;
import com.example.shop_java.web.dto.SeoDto;
import com.example.shop_java.web.mapper.SeoMapper;
import com.example.shop_java.web.response.seo.SeoListResponse;
import com.example.shop_java.web.response.seo.SeoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seo")
@RequiredArgsConstructor
public class SeoController {

    private final SeoService seoService;

    private final SeoMapper seoMapper;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SeoListResponse> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.seoMapper.seoListToSeoListResponse(this.seoService.findAll(pageable))
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SeoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.seoMapper.seoToResponse(this.seoService.findById(id))
        );
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SeoResponse> create(@RequestBody SeoDto seoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                seoMapper.seoToResponse(
                        seoService.save(seoMapper.requestToSeo(seoDto))
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SeoResponse> update(@PathVariable Long id, @RequestBody SeoDto seoDto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.seoMapper.seoToResponse(
                        this.seoService.update(this.seoMapper.requestToSeo(seoDto, id))
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.seoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
