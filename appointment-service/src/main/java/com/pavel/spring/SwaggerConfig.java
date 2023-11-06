package com.pavel.spring;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact() {{
            setName("Pavel");
            setEmail("pnnenov@gmail.com");
        }};

        Info info = new Info()
                .title("Appointments App")
                .version("0.0.1")
                .contact(contact)
                .description("Appointment service.");

        return new OpenAPI().info(info);
    }
}
