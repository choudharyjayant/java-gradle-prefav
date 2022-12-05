
package com.poc.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.handlers.SlateHandler;
import com.poc.models.Slate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration
public class SlateRouter {

	@RouterOperations({ @RouterOperation(path = "/projects/{projectId}/slates", produces = {
			MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = SlateHandler.class, beanMethod = "getSlatesByProjectId",

			operation = @Operation(operationId = "getSlatesByProjectId", responses = {
					@ApiResponse(responseCode = "200", description = "Get all slates by project Id", content = @Content(schema = @Schema(implementation = Slate.class))) }, parameters = {
							@Parameter(in = ParameterIn.PATH, name = "projectId") }

			)

			),

			@RouterOperation(path = "/projects/{projectId}/slates", produces = {
					MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, beanClass = SlateHandler.class, beanMethod = "addSlate",

					operation = @Operation(operationId = "addSlate", responses = {
							@ApiResponse(responseCode = "200", description = "add slates by slate Id", content = @Content(schema = @Schema(implementation = Slate.class))) }, parameters = {
									@Parameter(in = ParameterIn.PATH, name = "projectId") }, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Slate.class))))

			), @RouterOperation(path = "/projects/{projectId}/slates/{slateId}", produces = {
					MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = SlateHandler.class, beanMethod = "getSlateById",

					operation = @Operation(operationId = "getSlateById", responses = {
							@ApiResponse(responseCode = "200", description = "Get all slates by slate Id", content = @Content(schema = @Schema(implementation = Slate.class))) }, parameters = {
									@Parameter(in = ParameterIn.PATH, name = "projectId"),
									@Parameter(in = ParameterIn.PATH, name = "slateId") }

					)

			), @RouterOperation(path = "/projects/{projectId}/slates/{slateId}", produces = {
					MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE, beanClass = SlateHandler.class, beanMethod = "deleteSlateById",

					operation = @Operation(operationId = "deleteSlateById", responses = {
							@ApiResponse(responseCode = "200", description = "Get all slates by slate Id", content = @Content(schema = @Schema(implementation = Slate.class))) }, parameters = {
									@Parameter(in = ParameterIn.PATH, name = "projectId"),
									@Parameter(in = ParameterIn.PATH, name = "slateId") }

					)

			), @RouterOperation(path = "/projects/{projectId}/slates/{slateId}", produces = {
					MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT, beanClass = SlateHandler.class, beanMethod = "updateSlate",

					operation = @Operation(operationId = "updateSlate", responses = {
							@ApiResponse(responseCode = "200", description = "update slates by slate Id", content = @Content(schema = @Schema(implementation = Slate.class))) }, parameters = {
									@Parameter(in = ParameterIn.PATH, name = "projectId"),
									@Parameter(in = ParameterIn.PATH, name = "slateId") }, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Slate.class))))

			) })

	@Bean
	public RouterFunction<ServerResponse> getResponses(SlateHandler slateHandler) {
		return RouterFunctions.route(GET("/projects/{projectId}/slates"), slateHandler::getSlatesByProjectId)
				.andRoute(POST("/projects/{projectId}/slates"), slateHandler::addSlate)
				.andRoute(PUT("/projects/{projectId}/slates/{slateId}"), slateHandler::updateSlate)
				.andRoute(GET("/projects/{projectId}/slates/{slateId}"), slateHandler::getSlateById)
				.andRoute(DELETE("/projects/{projectId}/slates/{slateId}"), slateHandler::deleteSlateById);

	}

}
