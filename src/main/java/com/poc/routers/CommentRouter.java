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

import com.poc.handlers.CommentHandler;
import com.poc.models.Comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration
public class CommentRouter {
	
	@RouterOperations({
		@RouterOperation(
				path = "/elements/{elementId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.GET,
				beanClass = CommentHandler.class,
				beanMethod = "getCommentsByElementId",
				operation = @Operation(
						operationId = "getCommentsByElementId",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "All comments by element id fetched successfully",
										content = @Content(schema = @Schema(
												implementation = Comment.class
										))
								),
								@ApiResponse(
										responseCode = "404",
										description = "Comments by element id not found"
										)
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "elementId")
						}
				)
				
		),
		@RouterOperation(
				path = "/elements/{elementId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.POST,
				beanClass = CommentHandler.class,
				beanMethod = "addCommentByElementId",
				operation = @Operation(
						operationId = "addCommentByElementId",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Comment by element id added successfully",
										content = @Content(schema = @Schema(
												implementation = Comment.class
												))
										),
								@ApiResponse(
										responseCode = "404",
										description = "Unable to add comment comment, Element id not found"
										)
						},
						requestBody = @RequestBody(
							content = @Content(schema = @Schema(
										implementation = Comment.class
									))
						),
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "elementId")
						}
				)
		),
		@RouterOperation(
				path = "/elements/{elementId}/comments/{commentId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.PUT,
				beanClass = CommentHandler.class,
				beanMethod = "editComment",
				operation = @Operation(
						operationId = "editComment",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Comment by elementId and commentId updated",
										content = @Content(schema = @Schema(
												implementation = Comment.class
												))
										),	
								@ApiResponse(
										responseCode = "404",
										description = "Unable to edit comment comment, comment not found"
										)
						},
						requestBody = @RequestBody(
							content = @Content(schema = @Schema(
										implementation = Comment.class
									))
						),
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "elementId"),
								@Parameter(in = ParameterIn.PATH, name = "commentId")
						}
				)
		
		),
		
		@RouterOperation(
				path = "/elements/{elementId}/comments/{commentId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.DELETE,
				beanClass = CommentHandler.class,
				beanMethod = "deleteComment",
				operation = @Operation(
						operationId = "deleteComment",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Comment by elementId and commentId deleted",
										content = @Content(schema = @Schema(
												implementation = Comment.class
												))
										),
								@ApiResponse(
										responseCode = "404",
										description = "Unable to deleted comment comment, comment not found"
										)
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "elementId"),
								@Parameter(in = ParameterIn.PATH, name = "commentId")
						}
					)
			)
	})
	
	@Bean
	public RouterFunction<ServerResponse> commentAPIs(CommentHandler commentHandler){
		return route(GET("/elements/{elementId}"), commentHandler :: getCommentsByElementId)
				.andRoute(POST("/elements/{elementId}"), commentHandler :: addCommentByElementId)
				.andRoute(PUT("/elements/{elementId}/comments/{commentId}"), commentHandler :: editComment)
				.andRoute(DELETE("/elements/{elementId}/comments/{commentId}"), commentHandler :: deleteComment);			
	}

}
