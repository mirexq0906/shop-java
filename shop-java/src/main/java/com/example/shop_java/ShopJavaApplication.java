package com.example.shop_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShopJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopJavaApplication.class, args);
    }

}
