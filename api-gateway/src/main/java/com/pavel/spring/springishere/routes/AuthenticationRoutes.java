package com.pavel.spring.springishere.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.*;
import org.springframework.cloud.gateway.route.builder.*;
import org.springframework.context.annotation.*;


@Configuration
public class AuthenticationRoutes {

    @Value("${api-gateway.authentication-client-uri}")
    private String authenticationClientUri;

    @Bean
    public RouteLocator authenticationRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/app/api/v1/auth/**")
//                        .filters(f -> f.)
//                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(authenticationClientUri)
                )
                .route(p -> p
                        .path("/app/api/v1/greeting")
                        .uri(authenticationClientUri)
                ).route(p -> p
                        .path("/app/api/v1/greeting/say-goodbye")
                        .uri(authenticationClientUri)
                )
                .route(p -> p
                        .path("/auth-service-api-docs")
                        .filters(f -> f
                                .rewritePath("/auth-service-api-docs", "/app/v3/api-docs"))
                        .uri(authenticationClientUri))

                .route(p -> p
                        .host("*.circuitbreaker.com")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("mycmd")
                                        .setFallbackUri("forward:/fallback")))
                        .uri(authenticationClientUri))
                .route(p -> p
                        .path("/app/heartbeat")
                        .uri(authenticationClientUri))

                .build();
    }
}
