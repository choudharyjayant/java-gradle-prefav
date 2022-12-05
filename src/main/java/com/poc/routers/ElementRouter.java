package com.poc.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.handlers.ElementHandler;
import com.poc.models.Element;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration
public class ElementRouter {
	
	@Autowired
	
	private ElementHandler elementHandler;

	@RouterOperations({
		
		@RouterOperation(
				path = "/projects/{projectId}/slates/{slateId}/elements",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.GET,
				beanClass = ElementHandler.class,
				beanMethod = "getElementByProjectIdandSlateId",
				operation = @Operation(
						operationId = "getElementByProjectIdandSlateId",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Elements fetched Successfully",
										content = @Content(
												schema = @Schema(
														implementation = Element.class
														)
												
												)
										),
								@ApiResponse(
										responseCode = "404",
										description = "Not found")
								
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "projectId"),
								@Parameter(in = ParameterIn.PATH, name = "slateId")
						}
						
						)
						),
				
		
		@RouterOperation(
				path = "/projects/{projectId}/slates/{slateId}/elements",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.POST,
				beanClass = ElementHandler.class,
				beanMethod = "addElementbyProjectandSlateId",
				operation = @Operation(
						operationId = "addElementbyProjectandSlateId",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Element is added successfully",
										content = @Content(schema = @Schema(
												implementation = Element.class
												))
										),
								@ApiResponse(
										responseCode = "404",
										description = "Not found"
										)
						},
						requestBody = @RequestBody(
							content = @Content(schema = @Schema(
										implementation = Element.class
									))
						),
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "projectId"),
								@Parameter(in = ParameterIn.PATH, name = "slateId")
						}
				)
		),
		@RouterOperation(
				path = "/projects/{projectId}/slates/{slateId}/elements/{elementId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.PUT,
				beanClass = ElementHandler.class,
				beanMethod = "updateElement",
				operation = @Operation(
						operationId = "updateElement",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Element is updated successfully",
										content = @Content(schema = @Schema(
												implementation = Element.class
												))
										),
								@ApiResponse(
										responseCode = "404",
										description = "Element not found, update is not possible"
										)
						},
						requestBody = @RequestBody(
							content = @Content(schema = @Schema(
										implementation = Element.class
									))
						),
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "projectId"),
								@Parameter(in = ParameterIn.PATH, name = "slateId"),
								@Parameter(in = ParameterIn.PATH, name = "elementId")
						}
				)
		
		),
		
		@RouterOperation(
				path = "/projects/{projectId}/slates/{slateId}/elements/{elementId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.GET,
				beanClass = ElementHandler.class,
				beanMethod = "getElementsByElementId",
				operation = @Operation(
						operationId = "getElementsByElementId",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "elements fetched successfully",
										content = @Content(schema = @Schema(
												implementation = Element.class
										))
								),
								@ApiResponse(
										responseCode = "404",
										description = "Not found"
										)
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "projectId"),
								@Parameter(in = ParameterIn.PATH, name = "slateId"),
								@Parameter(in = ParameterIn.PATH, name = "elementId")
						}
				)
				
		),
		
		@RouterOperation(
				path = "/projects/{projectId}/slates/{slateId}/elements/{elementId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.DELETE,
				beanClass = ElementHandler.class,
				beanMethod = "deleteElementById",
				operation = @Operation(
						operationId = "deleteElementById",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Deleted",
										content = @Content(schema = @Schema(
												implementation = Element.class
												))
										),
								@ApiResponse(
										responseCode = "404",
										description = "Unable to delete"
										)
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "projectId"),
								@Parameter(in = ParameterIn.PATH, name = "slateId"),
								@Parameter(in = ParameterIn.PATH, name = "elementId")
						}
					)
			)
	})


	@Bean
	public RouterFunction<ServerResponse> elementAPIs() {
		return route(POST("/projects/{projectId}/slates/{slateId}/elements"),
				elementHandler::addElementbyProjectandSlateId)
				.andRoute(GET("/projects/{projectId}/slates/{slateId}/elements"),
						elementHandler::getElementByProjectIdandSlateId)
				.andRoute(PUT("/projects/{projectId}/slates/{slateId}/elements/{elementId}"),
						elementHandler::updateElement)
				.andRoute(DELETE("/projects/{projectId}/slates/{slateId}/elements/{elementId}"),
						elementHandler::deleteElementById)
				.andRoute(GET("/projects/{projectId}/slates/{slateId}/elements/{elementId}"),
						elementHandler::getElementsByElementId);

	}
}
