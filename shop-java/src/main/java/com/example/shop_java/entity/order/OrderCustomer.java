package com.example.shop_java.entity.order;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_customers")
@Data
public class OrderCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String phone;

    private String address;

}
