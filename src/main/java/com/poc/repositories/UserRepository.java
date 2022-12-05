package com.poc.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.poc.models.User;

import reactor.core.publisher.Mono;
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String>{
	//Mono<User> findByName(String name);
	Mono<User> findByUserName(String userName);
}
