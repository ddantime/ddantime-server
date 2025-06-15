package com.ddantime.ddantime.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${SWAGGER_SERVER_URL}")
    private String swaggerServerUrl;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url(swaggerServerUrl))
                .info(new Info()
                        .title("딴타임 API 명세서")
                        .version("v1.0"));
    }
}