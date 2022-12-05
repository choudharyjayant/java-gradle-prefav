package com.poc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Autowired
	private com.poc.security.AuthenticationManager authenticationManager;
	@Autowired
	private SecurityContextRepository securityContextRepository;

	@Bean
	SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
		String[] patterns = new String[] { "/auth/**" };
		return http.cors().disable().exceptionHandling()
				.authenticationEntryPoint((exchange, e) -> Mono.fromRunnable(() -> {
					exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				})).accessDeniedHandler((exchange, e) -> Mono.fromRunnable(() -> {
					exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
				})).and().csrf().disable().authenticationManager(authenticationManager)
				.securityContextRepository(securityContextRepository).authorizeExchange().pathMatchers(patterns)
				.permitAll().pathMatchers(HttpMethod.OPTIONS).permitAll().anyExchange().authenticated().and().build();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();

	}

}