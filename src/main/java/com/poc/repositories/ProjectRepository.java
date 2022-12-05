package com.poc.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.poc.models.Project;

@Repository
public interface ProjectRepository extends ReactiveMongoRepository<Project,String> {

}