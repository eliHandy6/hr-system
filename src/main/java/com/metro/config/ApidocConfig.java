package com.metro.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/

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
    public GroupedOpenApi commonSetups() {
        String[] paths = {"/api/v1/marital-status/**",
                "/api/v1/ethnicity/**",
                "/api/v1/nationality/**"
                , "/api/v1/district/**",
                "/api/v1/title/**"
        };
        return GroupedOpenApi.builder()
                .group("Common-Setups")
                .pathsToMatch(paths)
                .build();
    }


    @Bean
    public GroupedOpenApi hrSetups() {
        String[] paths = {"/api/v1/staff-category/**",
                "/api/v1/job-groups/**",
                "/api/v1/leave-category/**",
                "/api/v1/department/**",
                "/api/v1/section/**",
                "/api/v1/designation/**",
                "/api/v1/business-units/**",
<<<<<<< HEAD
                "/api/v1/jobcategory/**",
                "/api/v1/jobsubcategory/**"
=======
                "/api/v1/payroll-groups/**",
                "/api/v1/notice-periods/**"
>>>>>>> main
        };
        return GroupedOpenApi.builder()
                .group("Hr-Setups")
                .pathsToMatch(paths)
                .build();
    }


}
