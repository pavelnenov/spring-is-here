package com.pavel.spring.routes;

import org.springframework.cloud.gateway.route.*;
import org.springframework.cloud.gateway.route.builder.*;
import org.springframework.context.annotation.*;


@Configuration
public class Routes {

    @Bean
    public RouteLocator authenticationRoute(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String httpUri = uriConfiguration.getDestination();
        return builder.routes()
                .route(p -> p
                        .path("/app/api/v1/auth/**")
//                        .filters(f -> f.)
//                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpUri)
                )
//                .route(p -> p
//                        .path("/app/v2/api-docs")
//                        .uri(httpUri))
                .route(p -> p
                        .path("/auth-service-api-docs")
                        .filters(f -> f
                                .rewritePath("/auth-service-api-docs", "/app/v2/api-docs"))
                        .uri(httpUri))
                .route(p -> p
                        .host("*.circuitbreaker.com")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("mycmd")
                                        .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .build();
    }
}
