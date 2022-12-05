package com.poc.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(
		title = "POC - Project APIs",
		version = "1.0",
		description = "Project API endpoints for Java POC"
		))
public class SwaggerConfig {

}
