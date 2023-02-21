package com.metro.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@OpenAPIDefinition
@Configuration
public class ApidocConfig {
    @Bean
    public OpenAPI baseOpenAPI() throws IOException {

        Components components = new Components();

        return new OpenAPI()
                .components(components)
                .info(new Info().title("Metropolitan_hr").version("1.0.0").description("human resource app"));
    }

    @Bean
    public GroupedOpenApi authenticationApi() {
        String[] paths = {"/api/v1/auth/**"};
        return GroupedOpenApi.builder()
                .group("Authentication")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi postApi() {
        String[] paths = {"/post/**"};
        return GroupedOpenApi.builder()
                .group("Post")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi roleApi() {
        String[] paths = {"/api/v1/roles/**"};
        return GroupedOpenApi.builder()
                .group("Roles")
                .pathsToMatch(paths)
                .build();
    }


    @Bean
    public GroupedOpenApi staffSetups() {
        String[] paths = {"/api/v1/staff-category/**"};
        return GroupedOpenApi.builder()
                .group("Staff")
                .pathsToMatch(paths)
                .build();
    }

}
