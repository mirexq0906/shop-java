package com.example.shop_java.repository;

import com.example.shop_java.entity.Seo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeoRepository extends JpaRepository<Seo, Long> {

    Optional<Seo> findByUri(String uri);

}
