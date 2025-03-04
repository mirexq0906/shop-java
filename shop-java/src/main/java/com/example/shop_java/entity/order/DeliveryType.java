package com.example.shop_java.entity.order;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "delivery_types")
@Data
public class DeliveryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false, name = "with_address")
    private Boolean withAddress = false;

}
