package com.example.shop_java.service.event;

import com.example.shop_java.event.TrafficSendEvent;
import com.example.shop_java.message.TrafficMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrafficEventService {

    private final ApplicationEventPublisher publisher;

    public void sendEvent(Long productId, TrafficMessage.TrafficType trafficType) {
        this.publisher.publishEvent(new TrafficSendEvent(
                this,
                TrafficMessage.builder()
                        .id(UUID.randomUUID().toString())
                        .trafficType(trafficType)
                        .productId(productId)
                        .createdAt(LocalDateTime.now())
                        .build()
        ));
    }

}
