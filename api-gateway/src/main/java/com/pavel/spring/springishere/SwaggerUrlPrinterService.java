package com.pavel.spring.springishere;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SwaggerUrlPrinterService {

    private final Logger logger = LoggerFactory.getLogger(SwaggerUrlPrinterService.class);

    @Value("${swaggerUrl.url}")
    private String swaggerUrl;

    @EventListener(ApplicationReadyEvent.class)
    public void printSwaggerUrl() {
        logger.info("Swagger url: " + swaggerUrl);
    }
}
