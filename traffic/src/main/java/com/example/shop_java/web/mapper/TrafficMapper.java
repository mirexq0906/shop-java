package com.example.shop_java.web.mapper;

import com.example.shop_java.entity.Traffic;
import com.example.shop_java.entity.TrafficStatus;
import com.example.shop_java.message.TrafficMessage;
import org.springframework.stereotype.Component;

@Component
public class TrafficMapper {

    public Traffic trafficMessageToTraffic(TrafficMessage trafficMessage) {
        Traffic traffic = new Traffic();
        traffic.setId(trafficMessage.getId());
        traffic.setTrafficStatus(TrafficStatus.ACTIVE);
        traffic.setTrafficType(trafficMessage.getTrafficType());
        traffic.setProductId(trafficMessage.getProductId());
        traffic.setCreateAt(trafficMessage.getCreatedAt());
        return traffic;
    }

}
