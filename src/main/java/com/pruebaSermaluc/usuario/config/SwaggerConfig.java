package com.pruebaSermaluc.usuario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("users")  // Nombre del grupo en Swagger
                .pathsToMatch("/api/**")  // Rutas a documentar
                .build();
    }
}
