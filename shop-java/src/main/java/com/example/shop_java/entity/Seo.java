package com.example.shop_java.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "seos")
@Data
public class Seo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uri;

    private String title;

    private String description;

    private String keywords;

}
