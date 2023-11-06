package com.pavel.spring.springishere;


import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig2 {

//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .servers(List.of(
//                        new Server().url("http://localhost:8081").description("Authentication server"),
//                        new Server().url("http://localhost:8082").description("Appointments server")
//                ))
//                .info(new Info()
//                        .title("API Gateway")
//                        .description("API Gateway")
//                        .version("1.0")
//                );
//    }

    @Bean
    public GroupedOpenApi appointmentServiceOpenApi() {
        String packagesToscan = "com.pavel.spring.springishere.controllers";
        return GroupedOpenApi.builder()
                .group("AppointmentService")
                .pathsToMatch("/appointment-service-api-docs")
                .packagesToScan(packagesToscan)
                .build();
    }

    @Bean
    public GroupedOpenApi authenticationClientOpenApi() {
        String packagesToscan = "com.pavel.spring.springishere.controller";
        return GroupedOpenApi.builder()
                .group("AuthenticationClient")
                .pathsToMatch("/auth-service-api-docs")
                .packagesToScan(packagesToscan)
                .build();
    }


}

