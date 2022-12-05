package com.poc.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.web.reactive.function.server.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import com.poc.dto.ReplyDto;
import com.poc.handlers.ReplyHandler;

@Configuration
public class ReplyRouter {

	@RouterOperations({

			// GET
			@RouterOperation(path = "/elements/{elementId}/comments/{commentId}", produces = {
					MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET, beanClass = ReplyHandler.class, beanMethod = "getReplyByCommentId", operation = @Operation(operationId = "getReplyByCommentId",

							responses = {
									@ApiResponse(responseCode = "200", description = "Fetched successfully", content = @Content(schema = @Schema(implementation = ReplyDto.class))),
									@ApiResponse(responseCode = "404", description = "Not found") },
							

							parameters = {
									@Parameter(in = ParameterIn.PATH, name = "elementId"),
									@Parameter(in = ParameterIn.PATH, name = "commentId") })

			),

			// POST
			@RouterOperation(path = "/elements/{elementId}/comments/{commentId}", produces = {
					MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST, beanClass = ReplyHandler.class, beanMethod = "addReply", operation = @Operation(operationId = "addReply",

							responses = {
									@ApiResponse(responseCode = "200", description = "Reply is added successfully", content = @Content(schema = @Schema(implementation = ReplyDto.class))),
									@ApiResponse(responseCode = "404", description = "Not found") }, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = ReplyDto.class))),

							parameters = { @Parameter(in = ParameterIn.PATH, name = "elementId"),
									@Parameter(in = ParameterIn.PATH, name = "commentId") })),

			// PUT
			@RouterOperation(path = "/elements/{elementId}/comments/{commentId}/replies/{replyId}", produces = {
					MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT, beanClass = ReplyHandler.class, beanMethod = "updateReply", operation = @Operation(operationId = "updateReply",

							responses = {
									@ApiResponse(responseCode = "200", description = "Reply is updated successfully", content = @Content(schema = @Schema(implementation = ReplyDto.class))),
									@ApiResponse(responseCode = "404", description = "Reply not found, update is not possible") }, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = ReplyDto.class))),

							parameters = { @Parameter(in = ParameterIn.PATH, name = "elementId"),
									@Parameter(in = ParameterIn.PATH, name = "commentId"),
									@Parameter(in = ParameterIn.PATH, name = "replyId") })

			),

			// DELETE
			@RouterOperation(path = "/elements/{elementId}/comments/{commentId}/replies/{replyId}", produces = {
					MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.DELETE, beanClass = ReplyHandler.class, beanMethod = "deleteReplyById", operation = @Operation(operationId = "deleteReplyById",

							responses = {
									@ApiResponse(responseCode = "200", description = "Deleted", content = @Content(schema = @Schema(implementation = ReplyDto.class))),
									@ApiResponse(responseCode = "404", description = "Unable to delete") },

							parameters = { @Parameter(in = ParameterIn.PATH, name = "elementId"),
									@Parameter(in = ParameterIn.PATH, name = "commentId"),
									@Parameter(in = ParameterIn.PATH, name = "replyId") })) })

	@Bean
	public RouterFunction<ServerResponse> replyAPIs(ReplyHandler replyHandler) {
		return route(GET("/elements/{elementId}/comments/{commentId}"), replyHandler::getReplyByCommentId)
				.andRoute(POST("/elements/{elementId}/comments/{commentId}"), replyHandler::addReply)
				.andRoute(PUT("/elements/{elementId}/comments/{commentId}/replies/{replyId}"), replyHandler::updateReply)
				.andRoute(DELETE("/elements/{elementId}/comments/{commentId}/replies/{replyId}"), replyHandler::deleteReplyById);
	}

}
