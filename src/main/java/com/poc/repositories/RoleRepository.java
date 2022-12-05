package com.poc.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.poc.models.Role;

import reactor.core.publisher.Mono;

public interface RoleRepository extends ReactiveMongoRepository<Role, String> {

	Mono<Role> findByName(String name);

}
