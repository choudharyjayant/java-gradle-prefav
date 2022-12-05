
package com.poc.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.dto.SlateDTO;
import com.poc.models.Slate;
import com.poc.services.ISlateService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SlateHandler {

	@Autowired
	private ISlateService slateService;

	
	public Mono<ServerResponse> getSlatesByProjectId(ServerRequest request) {
		String projectId = request.pathVariable("projectId");
		Flux<Slate> slateFlux = slateService.getSlatesByProjectId(projectId);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(slateFlux, Slate.class);
	}
	
	
	public Mono<ServerResponse> addSlate(ServerRequest request) {
		String projectId = request.pathVariable("projectId");
		Mono<SlateDTO> slateDTOMono = request.bodyToMono(SlateDTO.class);
		return slateDTOMono.flatMap(slateDTO -> {
			return slateService.addSlate(slateDTO, projectId);
		}).flatMap(slate -> {
			return ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(slate), Slate.class);
		});

	}

	//@PreAuthorize("hasRole('admin')")
	public Mono<ServerResponse> updateSlate(ServerRequest request) {
		String slateId = request.pathVariable("slateId");
		String projectId = request.pathVariable("projectId");
		Mono<SlateDTO> slateDTOMono = request.bodyToMono(SlateDTO.class);
		return slateDTOMono.flatMap(slateDTO -> {
			return slateService.updateSlate(slateDTO, slateId, projectId);
		}).flatMap(slate -> {
			return ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
					.body(Mono.just(slate), Slate.class);
		});
	}

	public Mono<ServerResponse> getSlateById(ServerRequest request) {
		String slateId = request.pathVariable("slateId");
		Mono<Slate> slateMono = slateService.getSlateById(slateId);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(slateMono, Slate.class);
	}
	
	public Mono<ServerResponse> deleteSlateById(ServerRequest request) {
		String slateId = request.pathVariable("slateId");
		Mono<Void> slateMono = slateService.deleteSlateById(slateId);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(slateMono, Slate.class);

	}

}
