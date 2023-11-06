package com.pavel.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class AppointmentService {

    public static void main(String[] args) {
        SpringApplication.run(AppointmentService.class, args);
    }
}
