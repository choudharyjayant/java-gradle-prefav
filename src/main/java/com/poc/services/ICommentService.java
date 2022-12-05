package com.poc.services;

import com.poc.dto.CommentDTO;
import com.poc.models.Comment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICommentService {
	Flux<Comment> getCommentsByElementId(String elementId);

	Mono<Comment> addCommentByElementId(String elementId, CommentDTO commentDTO);

	Mono<Comment> editComment(String elementId, String commentId, CommentDTO commentDTO);

	Mono<Void> deleteCommentById(String elementId, String commentId);
}
