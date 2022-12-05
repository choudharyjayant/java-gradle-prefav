package com.poc.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.dto.CommentDTO;
import com.poc.models.Comment;
import com.poc.services.ICommentService;
import com.poc.utility.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CommentHandler {

	@Autowired
	private ICommentService commentService;

	// @PreAuthorize("hasRole('ADMIN')")
	public Mono<ServerResponse> getCommentsByElementId(ServerRequest serverRequest) {
		String elementId = serverRequest.pathVariable(Constants.ELEMENT_ID);

		Flux<Comment> commentFlux = commentService.getCommentsByElementId(elementId);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(commentFlux, Comment.class)
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	// @PreAuthorize("hasRole('ADMIN')")
	public Mono<ServerResponse> addCommentByElementId(ServerRequest serverRequest) {
		String elementId = serverRequest.pathVariable(Constants.ELEMENT_ID);
		Mono<CommentDTO> commentDTOMono = serverRequest.bodyToMono(CommentDTO.class);

		return commentDTOMono.flatMap(commentDTO -> {
			return commentService.addCommentByElementId(elementId, commentDTO);
		}).flatMap(comment -> {
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(comment), Comment.class);
		});
	}

	// @PreAuthorize("hasRole('ADMIN')")
	public Mono<ServerResponse> editComment(ServerRequest serverRequest) {
		String elementId = serverRequest.pathVariable(Constants.ELEMENT_ID);
		String commentId = serverRequest.pathVariable(Constants.COMMENT_ID);
		Mono<CommentDTO> commentDTOMono = serverRequest.bodyToMono(CommentDTO.class);

		return commentDTOMono.flatMap(commentDTO -> {
			return commentService.editComment(elementId, commentId, commentDTO);
		}).flatMap(comment -> {
			return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(comment), Comment.class);
		});
	}

	// @PreAuthorize("hasRole('ADMIN')")
	public Mono<ServerResponse> deleteComment(ServerRequest serverRequest) {
		String elementId = serverRequest.pathVariable(Constants.ELEMENT_ID);
		String commentId = serverRequest.pathVariable(Constants.COMMENT_ID);
		Mono<Void> commentMono = commentService.deleteCommentById(elementId, commentId);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(commentMono, Comment.class);
	}
}
