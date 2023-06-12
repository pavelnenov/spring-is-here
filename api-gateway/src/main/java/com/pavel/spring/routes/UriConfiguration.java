package com.pavel.spring.routes;

import org.springframework.context.annotation.*;

@Configuration
class UriConfiguration {

    private String destination = "http://localhost:8081";

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}