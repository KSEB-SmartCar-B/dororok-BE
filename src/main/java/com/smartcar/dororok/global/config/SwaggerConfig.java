package com.smartcar.dororok.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {

    private final String serverUrl = System.getenv("SERVER_URL");

    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );
        ArrayList<Server> servers = new ArrayList<>();
        servers.add(new Server().url("http://localhost:8080").description("Local Server"));
        servers.add(new Server().url("http://"+serverUrl).description("Server"));

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .servers(servers)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
    private Info apiInfo() {
        return new Info()
                .title("Dororok API") // API의 제목
                .description("Dororok API") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }


}
