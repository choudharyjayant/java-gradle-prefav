package com.poc.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.dto.ElementDto;
import com.poc.models.Element;
import com.poc.services.IElementService;
import com.poc.utility.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ElementHandler {

	@Autowired
	private IElementService elementService;

	// @PreAuthorize("hasRole('ADMIN')")
	public Mono<ServerResponse> addElementbyProjectandSlateId(ServerRequest serverRequest) {
		String projectId = serverRequest.pathVariable(Constants.PROJECT_ID);
		String slateId = serverRequest.pathVariable(Constants.SLATE_ID);
		
		Mono<ElementDto> elementDtoMono = serverRequest.bodyToMono(ElementDto.class);
		
		return elementDtoMono.flatMap(elementDto -> {
			return elementService.addElementByProjectandSlateId(projectId, slateId, elementDto);
		}).flatMap(element -> {
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(element), Element.class);
		});

	}

	// @PreAuthorize("hasRole('ADMIN')")
	public Mono<ServerResponse> getElementByProjectIdandSlateId(ServerRequest serverRequest) {
		String projectId = serverRequest.pathVariable(Constants.PROJECT_ID);
		String slateId = serverRequest.pathVariable(Constants.SLATE_ID);
		
		Flux<Element> elementflux = elementService.getElementByProjectIdandSlateId(projectId, slateId);
		
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(elementflux, Element.class);
	}

	// @PreAuthorize("hasRole('ADMIN')")
	public Mono<ServerResponse> updateElement(ServerRequest serverRequest) {
		String projectId = serverRequest.pathVariable(Constants.PROJECT_ID);
		String slateId = serverRequest.pathVariable(Constants.SLATE_ID);
		String elementId = serverRequest.pathVariable(Constants.ELEMENT_ID);
		
		Mono<ElementDto> elementDtoMono = serverRequest.bodyToMono(ElementDto.class);
		
		return elementDtoMono.flatMap(elementDto -> {
			return elementService.updateElement(projectId, slateId, elementId, elementDto);
		}).flatMap(element -> {
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(element), Element.class);
		});

	}

	// @PreAuthorize("hasRole('ADMIN')")
	public Mono<ServerResponse> deleteElementById(ServerRequest serverRequest) {
		String projectId = serverRequest.pathVariable(Constants.PROJECT_ID);
		String slateId = serverRequest.pathVariable(Constants.SLATE_ID);
		String elementId = serverRequest.pathVariable(Constants.ELEMENT_ID);
		
		Mono<Void> elements = elementService.deleteElementById(projectId, slateId, elementId);
		
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(elements, Element.class);

	}

	// @PreAuthorize("hasRole('ADMIN')")
	public Mono<ServerResponse> getElementsByElementId(ServerRequest serverRequest) {
		String projectId = serverRequest.pathVariable(Constants.PROJECT_ID);
		String slateId = serverRequest.pathVariable(Constants.SLATE_ID);
		String elementId = serverRequest.pathVariable(Constants.ELEMENT_ID);
		
		Mono<Element> elementMono = elementService.getElementsByElementId(projectId, slateId, elementId);
		
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(elementMono, Element.class);
	}
}
