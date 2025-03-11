package com.example.shop_java.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = "app.rabbitmq")
public class AppRabbitmqProperties {

    private String host;

    @NestedConfigurationProperty
    private TrafficServiceRabbitmqProperties trafficService;

}
