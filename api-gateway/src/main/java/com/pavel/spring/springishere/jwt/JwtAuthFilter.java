package com.pavel.spring.springishere.jwt;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class JwtAuthFilter implements GlobalFilter {

    private static final List<String> SWAGGER_WHITELIST = List.of(
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
    );

    private static final String AUTH_ENDPOINT = "/app/api/v1/auth/authenticate";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // do not check auth for auth requests
        String path = exchange.getRequest().getPath().value();
        if (SWAGGER_WHITELIST.contains(path)) {
            return chain.filter(exchange);
        }

        //pass through auth requests
        if (path.equals(AUTH_ENDPOINT)) {
            ServerWebExchange build = exchange.mutate().request(r -> r.headers(h -> h.remove(AUTHORIZATION))).build();
            return chain.filter(build);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
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
