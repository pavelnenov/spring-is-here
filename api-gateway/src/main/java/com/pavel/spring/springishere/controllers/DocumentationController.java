package com.pavel.spring.springishere.controllers;


import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class DocumentationController  {

    @Bean
    public List<GroupedOpenApi> groupedOpenApis() {
        List<GroupedOpenApi> groups = new ArrayList<>();
        groups.add(GroupedOpenApi.builder().group("AppointmentService").pathsToMatch("/appointment-service-api-docs").build());
        groups.add(GroupedOpenApi.builder().group("AuthenticationClient").pathsToMatch("/auth-service-api-docs").build());
        return groups;
    }
}
