package com.example.shop_java.listener;

import com.example.shop_java.entity.Traffic;
import com.example.shop_java.message.TrafficMessage;
import com.example.shop_java.service.TrafficService;
import com.example.shop_java.web.mapper.TrafficMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

@Component
@RabbitListener(queues = "traffic.queue")
@RequiredArgsConstructor
public class ConsumerListener {

    private final TrafficService trafficService;
    private final TrafficMapper trafficMapper;

    int BATCH_SIZE = 5000;

    private final Queue<Traffic> queue = new ConcurrentLinkedQueue<>();

    @RabbitHandler
    public void receive(TrafficMessage message) {
        this.queue.add(
                this.trafficMapper.trafficMessageToTraffic(message)
        );

        if(queue.size() >= BATCH_SIZE) {
            this.saveTraffic();
        }
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void init() {
        this.saveTraffic();
    }

    private void saveTraffic() {
        List<Traffic> trafficEntities = new ArrayList<>();

        for (int i = 0; i < BATCH_SIZE && !this.queue.isEmpty(); i++) {
            trafficEntities.add(this.queue.poll());
        }

        if(!trafficEntities.isEmpty()) {
            this.trafficService.batchSave(trafficEntities);
        }
    }

}
