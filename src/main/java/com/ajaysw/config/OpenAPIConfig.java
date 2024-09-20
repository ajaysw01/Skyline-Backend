package com.ajaysw.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ajay Wankhade
 */
@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SkylineView API")
                        .version("1.0")
                        .description("API for managing hotel room bookings")
                        .contact(new Contact()
                                .name("Ajay Wankhade")
                                .email("ajaysw45@gmail.com")));
    }
}