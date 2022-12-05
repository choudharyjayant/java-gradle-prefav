package com.poc.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.poc.models.Element;

@Repository
public interface ElementRepository extends ReactiveMongoRepository<Element, String> {
}