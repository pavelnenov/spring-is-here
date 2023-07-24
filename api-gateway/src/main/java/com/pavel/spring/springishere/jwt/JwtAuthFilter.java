package com.pavel.spring.springishere.jwt;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class JwtAuthFilter implements GlobalFilter {

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/app/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/app/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/v2/api-docs/**",
            "/swagger-ui/**",

            // Auth controller
            "/**/auth/**",

            //heartbeat
            "/heartbeat"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // do not check auth for auth requests
        String path = exchange.getRequest().getPath().value();
        for (String s : AUTH_WHITELIST) {
            if (s.equals(path)) {
                return chain.filter(exchange);
            }
        }
//        if ("/app/api/v1/auth/**".equals(path)) {
//            return chain.filter(exchange);
//        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            String username = JwtUtil.extractUsername(jwtToken);

            if (username != null && JwtUtil.isTokenValid(jwtToken)) {
                return chain.filter(exchange);
            }
        }

        return this.onError(exchange, "Invalid JWT token", UNAUTHORIZED);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}