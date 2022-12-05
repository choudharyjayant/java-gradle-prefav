package com.poc.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.dto.ReplyDto;
import com.poc.services.ReplyServiceImpl;
import com.poc.utility.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ReplyHandler {

	@Autowired
	private ReplyServiceImpl replyService;

	public Mono<ServerResponse> getReplyByCommentId(ServerRequest serverRequest) {
		String elementId = serverRequest.pathVariable(Constants.ELEMENT_ID);
		String commentId = serverRequest.pathVariable(Constants.COMMENT_ID);
		Flux<ReplyDto> replyFlux = replyService.getReplyByCommentId(elementId, commentId);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(replyFlux, ReplyDto.class);
	}

	public Mono<ServerResponse> addReply(ServerRequest serverRequest) {
		String elementId = serverRequest.pathVariable(Constants.ELEMENT_ID);
		String commentId = serverRequest.pathVariable(Constants.COMMENT_ID);
		Mono<ReplyDto> replyDtoMono = serverRequest.bodyToMono(ReplyDto.class);

		return replyDtoMono.flatMap(dto -> {
			return replyService.addReply(elementId, commentId, dto);
		}).flatMap(replyDto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(replyDto),
				ReplyDto.class));

	}

	public Mono<ServerResponse> updateReply(ServerRequest serverRequest) {
		String elementId = serverRequest.pathVariable(Constants.ELEMENT_ID);
		String commentId = serverRequest.pathVariable(Constants.COMMENT_ID);
		String replyId = serverRequest.pathVariable(Constants.REPLY_ID);
		Mono<ReplyDto> replyDtoMono = serverRequest.bodyToMono(ReplyDto.class);

		return replyDtoMono.flatMap(dto -> {
			return replyService.updateReply(elementId, commentId, replyId, dto);
		}).flatMap(replyDto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(replyDto),
				ReplyDto.class));

	}

	public Mono<ServerResponse> deleteReplyById(ServerRequest serverRequest) {
		String elementId = serverRequest.pathVariable(Constants.ELEMENT_ID);
		String commentId = serverRequest.pathVariable(Constants.COMMENT_ID);
		String replyId = serverRequest.pathVariable(Constants.REPLY_ID);
		Mono<Void> replyDtoMono = replyService.deleteReplyById(elementId, commentId, replyId);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(replyDtoMono, ReplyDto.class);
	}

}
