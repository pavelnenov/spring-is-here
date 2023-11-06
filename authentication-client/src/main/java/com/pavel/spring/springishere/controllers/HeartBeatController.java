package com.pavel.spring.springishere.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartBeatController {

    @GetMapping("/heartbeat")
    public String heartbeat() {
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getForObject("http://localhost:8080/heartbeat", String.class);
        return "OK";
    }
}
