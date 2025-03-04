package com.example.shop_java.event;

import com.example.shop_java.message.TrafficMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TrafficSendEvent extends ApplicationEvent {

    private final TrafficMessage traffic;

    public TrafficSendEvent(Object source, TrafficMessage traffic) {
        super(source);
        this.traffic = traffic;
    }

}
