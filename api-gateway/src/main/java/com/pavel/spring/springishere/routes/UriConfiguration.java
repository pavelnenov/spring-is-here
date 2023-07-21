package com.pavel.spring.springishere.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration

class UriConfiguration {

    @Value("${api-gateway.authentication-client-uri}")
    private String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}