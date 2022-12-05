package com.poc.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.poc.models.Comment;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

}
