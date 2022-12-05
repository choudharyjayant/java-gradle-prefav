package com.poc.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poc.dto.ProjectDto;
import com.poc.models.Project;
import com.poc.repositories.ProjectRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectService implements IProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	private String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	private String date() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
	}

	@Override
	public Flux<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	@Override
	public Mono<Project> getProjectById(String id) {
		return projectRepository.findById(id);
	}

	@Override
	public Mono<Project> addProject(ProjectDto projectDto) {

		Project project = new Project();
		project.setDistributableUrn(uuid());
		project.setEntityUrn(uuid());
		project.setCreatedAt(date());
		project.setUpdatedAt(date());
		project.setAuthor(projectDto.getAuthor());
		project.setTitle(projectDto.getTitle());
		project.setCreatedBy(projectDto.getCreatedBy());
		project.setUpdatedBy(projectDto.getUpdatedBy());
		return projectRepository.save(project);
	}

	@Override
	public Mono<Project> updateProjectById(String id, ProjectDto projectDto) {

		if (projectRepository.findById(id).hasElement() != null)
			projectDto.setDistributableUrn(id);

		Project project = new Project();
		project.setEntityUrn(uuid());
		project.setCreatedAt(date());
		project.setUpdatedAt(date());
		project.setAuthor(projectDto.getAuthor());
		project.setTitle(projectDto.getTitle());
		project.setCreatedBy(projectDto.getCreatedBy());
		project.setUpdatedBy(projectDto.getUpdatedBy());

		return projectRepository.save(project);

	}

	@Override
	public Mono<Void> deleteProjectById(String id) {

		return projectRepository.deleteById(id);
	}
}
