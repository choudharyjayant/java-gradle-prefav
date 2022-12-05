package com.poc.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.dto.ProjectDto;
import com.poc.models.Project;
import com.poc.services.IProjectService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProjectHandler {

	private Mono<ServerResponse> notFound = ServerResponse.notFound().build();
	
	@Autowired
	private IProjectService projectService;
	
	public Mono<ServerResponse> getAllProjects(ServerRequest request)
	{
		Flux<Project> projectFlux = projectService.getAllProjects();
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(projectFlux,Project.class);
	}
	
	public Mono<ServerResponse> addProject(ServerRequest request)
	{
		Mono<ProjectDto> projectDtoMono = request.bodyToMono(ProjectDto.class);
		
		return projectDtoMono.flatMap(dto -> {
			return projectService.addProject(dto);
		}).flatMap(projectDto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(projectDto),
				ProjectDto.class));
	}
	
	public Mono<ServerResponse> getProjectById(ServerRequest request)
	{
		String projectId = request.pathVariable("projectId");
		
		Mono<Project> projectMono = projectService.getProjectById(projectId);
		
		return projectMono.flatMap(resp -> ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(projectMono, Project.class))
				.switchIfEmpty(notFound);
	}
	
	public Mono<ServerResponse> updateProjectById(ServerRequest request)
	{
		String id = request.pathVariable("projectId");
		Mono<ProjectDto> projectDtoMono = request.bodyToMono(ProjectDto.class);
		
		return projectDtoMono.flatMap(resp -> ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(projectService.updateProjectById(id, resp), Project.class))
				.switchIfEmpty(notFound);
	}
	
	public Mono<ServerResponse> deleteProjectById(ServerRequest request)
	{
		String id = request.pathVariable("projectId");
		Mono<Void> projectMono = projectService.deleteProjectById(id);
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(projectMono, Project.class)
				.switchIfEmpty(notFound);
	}
}
