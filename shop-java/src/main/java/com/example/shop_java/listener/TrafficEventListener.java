package com.example.shop_java.listener;

import com.example.shop_java.event.TrafficSendEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrafficEventListener implements ApplicationListener<TrafficSendEvent> {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void onApplicationEvent(TrafficSendEvent event) {
        this.rabbitTemplate.convertAndSend("traffic.queue", event.getTraffic());
    }

}
