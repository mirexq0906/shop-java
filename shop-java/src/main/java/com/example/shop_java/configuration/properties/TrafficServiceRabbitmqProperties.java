package com.example.shop_java.configuration.properties;

import lombok.Data;

@Data
public class TrafficServiceRabbitmqProperties {

    private String queue;

    private String exchange;

    private String routingKey;

}
