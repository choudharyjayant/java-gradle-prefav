
package com.poc.routers;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

import com.poc.form.LogInForm;
import com.poc.handlers.LoginHandler;
import com.poc.models.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration
public class LoginRouter {

	@Autowired
	LoginHandler loginHandler;

	@RouterOperations({ @RouterOperation(path = "/auth/login", produces = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, beanClass = LoginHandler.class, beanMethod = "loginup",

			operation = @Operation(operationId = "login", responses = {

					@ApiResponse(responseCode = "200", description = "authenticate user based on given creadentials", content = @Content(schema = @Schema(implementation = LogInForm.class))) })

			), @RouterOperation(path = "/auth/signup", produces = {
					MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, beanClass = LoginHandler.class, beanMethod = "signup",

					operation = @Operation(operationId = "signup", responses = {

							@ApiResponse(responseCode = "200", description = "authenticate user based on given creadentials", content = @Content(schema = @Schema(implementation = User.class))) })

			) })

	@Bean
	public RouterFunction<ServerResponse> authRoute() {
		return RouterFunctions.route(POST("/auth/login"), loginHandler::login).andRoute(POST("/auth/signup"),
				loginHandler::signUp);
	}

}
