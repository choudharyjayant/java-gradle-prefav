package com.poc.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.poc.dto.ProjectDto;
import com.poc.models.Project;
import com.poc.repositories.ProjectRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

	@InjectMocks
	private ProjectService projectService;

	@Mock
	private ProjectRepository projectRepository;

	String distributableUrn = UUID.randomUUID().toString();

	@Test
	public void getAllProjectsTest() {
		Project projectFlux = new Project("1", "01", "Project 01", "User01", "User123", "123", "User123", "123");

		Mockito.when(projectRepository.findAll()).thenReturn(Flux.just(projectFlux));

		Flux<Project> resultFlux = projectService.getAllProjects();

		StepVerifier.create(resultFlux).expectSubscription()
				.expectNext(new Project("1", "01", "Project 01", "User01", "User123", "123", "User123", "123"))
				.verifyComplete();
	}

	@Test
	public void getProjectByIdTest() {
		Project project = new Project("1", "01", "Project 01", "User01", "User123", "123", "User123", "123");

		Mockito.when(projectRepository.findById(distributableUrn)).thenReturn(Mono.just(project));

		Mono<Project> projectMono = projectService.getProjectById(distributableUrn);
		StepVerifier.create(projectMono).consumeNextWith(newProject -> {
			assertEquals("1", newProject.getDistributableUrn());
		}).verifyComplete();
	}

	@Test
	public void addProjectTest() {
		Project project = new Project("1", "01", "Project 01", "User01", "User123", "123", "User123", "123");
		ProjectDto projectDto = new ProjectDto("1", "01", "Project 01", "User01", "User123", "123", "User123", "123");
		Mockito.when(projectRepository.save(any(Project.class))).thenReturn(Mono.just(project));

		Mono<Project> projectMono = projectService.addProject(projectDto);

		projectMono.subscribe(res -> {
			assertTrue(res.getTitle().contains("Project 01"));
		});
	}

	@Test
	public void deleteProjectByIdTest() {

		Mockito.when(projectService.deleteProjectById(distributableUrn)).thenReturn(Mono.empty());
		Mono<Void> projectMono = projectService.deleteProjectById(distributableUrn);

		StepVerifier.create(projectMono).expectComplete().verify();
	}

	@Test
	public void updateProjectByIdTest() {
		Project project = new Project("1", "01", "Project 01", "User01", "User123", "123", "User123", "123");
		ProjectDto projectDto = new ProjectDto("1", "01", "Project 01 Updated", "User02", "User123", "123", "User123",
				"123");
		Mockito.when(projectRepository.findById("1")).thenReturn(Mono.just(project));
		Mockito.when(projectRepository.save(ArgumentMatchers.any(Project.class))).thenReturn(Mono.just(project));

		Mono<Project> resultMono = projectService.updateProjectById("1", projectDto);
		StepVerifier.create(resultMono).expectNextMatches(res -> res.getTitle().contains("Project 01"))
				.verifyComplete();
	}
}
