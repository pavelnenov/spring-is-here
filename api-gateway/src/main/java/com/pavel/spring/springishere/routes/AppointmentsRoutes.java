package com.pavel.spring.springishere.routes;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppointmentsRoutes {

    @Value("${api-gateway.appointment-service-uri}")
    private String appointmentServiceUri;

    @Bean
    public RouteLocator appointmentRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/appointment-service-api-docs")
                        .filters(f -> f
                                .rewritePath("/appointment-service-api-docs", "/app/v3/api-docs"))
                        .uri(appointmentServiceUri))
                .route(p -> p
                        .path("/doctors")
                        .filters(f -> f
                                .rewritePath("/doctors", "/app/doctors"))
                        .uri(appointmentServiceUri))
                .build();

    }
}
