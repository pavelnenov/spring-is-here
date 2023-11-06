package com.pavel.spring.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @KafkaListener(topics = "beers", groupId = "spring-boot-kafka")
    public void consume(ConsumerRecord<String, String> record) {
        System.out.println("Consumed message. Key = " + record.key() + ", value = " + record.value());
    }
}
