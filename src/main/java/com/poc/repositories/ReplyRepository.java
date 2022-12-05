package com.poc.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.poc.models.Reply;

@Repository
public interface ReplyRepository extends ReactiveMongoRepository<Reply, String>  {

}
