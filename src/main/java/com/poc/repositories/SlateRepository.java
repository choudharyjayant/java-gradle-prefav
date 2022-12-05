package com.poc.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.poc.models.Slate;

@Repository
public interface SlateRepository extends ReactiveMongoRepository<Slate, String> {

}
