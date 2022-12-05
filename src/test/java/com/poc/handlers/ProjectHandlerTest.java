package com.poc.handlers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;
import com.poc.dto.ProjectDto;
import com.poc.models.Project;
import com.poc.services.ProjectService;
import com.poc.utility.Constants;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class ProjectHandlerTest {

	@InjectMocks
	private ProjectHandler projectHandler;

	@Mock
	private ProjectService projectService;

	@Mock
	private ServerRequestWrapper serverRequestWrapper;

	@Test
	public void getAllProjectsTest() {
		when(projectService.getAllProjects()).thenReturn(Flux.just(new Project()));
		Mono<ServerResponse> serverResponse = projectHandler.getAllProjects(serverRequestWrapper);
		serverResponse.subscribe(res -> {
			assertEquals(HttpStatus.OK, res.statusCode());
		});
	}

	@Test
	public void getProjectByIdTest() {
		when(serverRequestWrapper.pathVariable(Constants.PROJECT_ID)).thenReturn(Constants.PROJECT_ID);
		when(projectService.getProjectById(ArgumentMatchers.anyString())).thenReturn(Mono.just(new Project()));
		Mono<ServerResponse> serverResponse = projectHandler.getProjectById(serverRequestWrapper);
		serverResponse.subscribe(res -> {
			assertEquals(HttpStatus.OK, res.statusCode());
		});
	}

	@Test
	public void addProjectTest() {
		// when(serverRequestWrapper.pathVariable(Constants.PROJECT_ID)).thenReturn(Constants.PROJECT_ID);
		when(serverRequestWrapper.bodyToMono(ProjectDto.class)).thenReturn(Mono.just(new ProjectDto()));
		when(projectService.addProject(ArgumentMatchers.any())).thenReturn(Mono.just(new Project()));
		Mono<ServerResponse> serverResponse = projectHandler.addProject(serverRequestWrapper);
		serverResponse.subscribe(res -> {
			assertEquals(HttpStatus.OK, res.statusCode());
		});
	}

	@Test
	public void updateProjectByIdTest() {
		when(serverRequestWrapper.pathVariable(Constants.PROJECT_ID)).thenReturn(Constants.PROJECT_ID);
		when(serverRequestWrapper.bodyToMono(ProjectDto.class)).thenReturn(Mono.just(new ProjectDto()));
		when(projectService.updateProjectById(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
				.thenReturn(Mono.just(new Project()));
		Mono<ServerResponse> serverResponse = projectHandler.updateProjectById(serverRequestWrapper);
		serverResponse.subscribe(res -> {
			assertEquals(HttpStatus.OK, res.statusCode());
		});
	}

	@Test
	public void deleteProjectByIdTest() {
		when(serverRequestWrapper.pathVariable(Constants.PROJECT_ID)).thenReturn(Constants.PROJECT_ID);
		when(projectService.deleteProjectById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());
		Mono<ServerResponse> serverResponse = projectHandler.deleteProjectById(serverRequestWrapper);
		serverResponse.subscribe(res -> {
			assertEquals(HttpStatus.OK, res.statusCode());
		});
	}
}
