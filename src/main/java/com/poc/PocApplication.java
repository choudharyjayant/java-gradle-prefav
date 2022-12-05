package com.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@EnableReactiveMethodSecurity
@EnableWebFlux
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Java POC", version = "1.0", description = "POST, GET, PUT, DELETE apis"))
public class PocApplication {
	public static void main(String[] args) {
		SpringApplication.run(PocApplication.class, args);
	}
}
