package com.poc.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import com.poc.models.Project;
import com.poc.handlers.ProjectHandler;

@Configuration
public class ProjectRouter {
	
	@RouterOperations({
		@RouterOperation(
				path = "/projects",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.GET,
				beanClass = ProjectHandler.class,
				beanMethod = "getAllProjects",
				operation = @Operation(
						operationId = "getAllProjects",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "All projects fetched successfully.",
										content = @Content(schema = @Schema(
												implementation = Project.class
										))
								)
						}
				)
		),
		@RouterOperation(
				path = "/projects/{projectId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.GET,
				beanClass = ProjectHandler.class,
				beanMethod = "getProjectById",
				operation = @Operation(
						operationId = "getProjectById",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Project detail by ID fetched successfully.",
										content = @Content(schema = @Schema(
												implementation = Project.class
										))
								),
								@ApiResponse(
										responseCode = "404",
										description = "Project not found with the given ID."
										)
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "projectId")
						}
				)
		),
		@RouterOperation(
				path = "/projects",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.POST,
				beanClass = ProjectHandler.class,
				beanMethod = "addProject",
				operation = @Operation(
						operationId = "addProject",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Project added successfully.",
										content = @Content(schema = @Schema(
												implementation = Project.class
												))
										)
						},
						requestBody = @RequestBody(
								content = @Content(schema = @Schema(
										implementation = Project.class
										)))
					)		
		),
		@RouterOperation(
				path = "/projects/{projectId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.PUT,
				beanClass = ProjectHandler.class,
				beanMethod = "updateProjectById",
				operation = @Operation(
						operationId = "updateProjectById",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Project updated successfully.",
										content = @Content(schema = @Schema(
												implementation = Project.class
												))
										),
								@ApiResponse(
										responseCode = "404",
										description = "Project not found with the given ID."
										)
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "projectId")
						},
						requestBody = @RequestBody(
								content = @Content(schema = @Schema(
										implementation = Project.class
										)))
					)		
		),
		@RouterOperation(
				path = "/projects/{projectId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.DELETE,
				beanClass = ProjectHandler.class,
				beanMethod = "deleteProjectById",
				operation = @Operation(
						operationId = "deleteProjectById",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Project deleted successfully.",
										content = @Content(schema = @Schema(
												implementation = Project.class
												))
										),
								@ApiResponse(
										responseCode = "404",
										description = "Project not found with the given ID."
										)
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "projectId")
						}
					)		
			)
	})
	
	@Bean
	public RouterFunction<ServerResponse> projectRoute(ProjectHandler projectHandler)
	{
		return RouterFunctions
				.route(GET("/projects").and(accept(MediaType.APPLICATION_JSON)), projectHandler::getAllProjects)
				.andRoute(POST("/projects").and(accept(MediaType.APPLICATION_JSON)), projectHandler::addProject)
				.andRoute(GET("/projects/{projectId}").and(accept(MediaType.APPLICATION_JSON)), projectHandler::getProjectById)
				.andRoute(PUT("/projects/{projectId}").and(accept(MediaType.APPLICATION_JSON)), projectHandler::updateProjectById)
				.andRoute(DELETE("/projects/{projectId}").and(accept(MediaType.APPLICATION_JSON)), projectHandler::deleteProjectById);
	}
	
}
