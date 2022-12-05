package com.poc.services;

import com.poc.dto.ProjectDto;
import com.poc.models.Project;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProjectService {

	Flux<Project> getAllProjects();
	Mono<Project> getProjectById(String id);
	Mono<Project> addProject(ProjectDto projectDto);
	Mono<Project> updateProjectById(String id, ProjectDto projectDto);
	Mono<Void> deleteProjectById(String id);
}