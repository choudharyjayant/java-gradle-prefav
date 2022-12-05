package com.poc.handlers;

import com.poc.dto.ReplyDto;

import com.poc.utility.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.services.ReplyServiceImpl;

import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReplyHandlerTest {
	@InjectMocks
	private ReplyHandler replyHandler;

	@Mock
	private ReplyServiceImpl replyService;

	@Mock
	private ServerRequestWrapper serverRequestWrapper;

	@Test
	public void getReplyByCommentIdTest() {
		when(serverRequestWrapper.pathVariable(Constants.ELEMENT_ID)).thenReturn(Constants.ELEMENT_ID);
		when(serverRequestWrapper.pathVariable(Constants.COMMENT_ID)).thenReturn(Constants.COMMENT_ID);
		when(replyService.getReplyByCommentId("elementId", "commentId")).thenReturn(Flux.just(new ReplyDto()));
		Mono<ServerResponse> serverResponse = replyHandler.getReplyByCommentId(serverRequestWrapper);
		serverResponse.subscribe(res -> {
			assertEquals(HttpStatus.OK, res.statusCode());
		});
	}

	@Test
	public void addReplyTest() {
		when(serverRequestWrapper.pathVariable(Constants.ELEMENT_ID)).thenReturn(Constants.ELEMENT_ID);
		when(serverRequestWrapper.pathVariable(Constants.COMMENT_ID)).thenReturn(Constants.COMMENT_ID);
		when(serverRequestWrapper.bodyToMono(ReplyDto.class)).thenReturn(Mono.just(new ReplyDto()));
		when(replyService.addReply(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any()))
				.thenReturn(Mono.just(new ReplyDto()));
		Mono<ServerResponse> serverResponse = replyHandler.addReply(serverRequestWrapper);
		serverResponse.subscribe(res -> {
			assertEquals(HttpStatus.OK, res.statusCode());
		});
	}

	@Test
	public void updateReplyTest() {
		when(serverRequestWrapper.pathVariable(Constants.ELEMENT_ID)).thenReturn(Constants.ELEMENT_ID);
		when(serverRequestWrapper.pathVariable(Constants.COMMENT_ID)).thenReturn(Constants.COMMENT_ID);
		when(serverRequestWrapper.pathVariable(Constants.REPLY_ID)).thenReturn(Constants.REPLY_ID);
		when(serverRequestWrapper.bodyToMono(ReplyDto.class)).thenReturn(Mono.just(new ReplyDto()));
		when(replyService.updateReply(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(Mono.just(new ReplyDto()));
		Mono<ServerResponse> serverResponse = replyHandler.updateReply(serverRequestWrapper);
		serverResponse.subscribe(res -> {
			assertEquals(HttpStatus.OK, res.statusCode());
		});
	}

	@Test
	public void deleteReplyByIdTest() {
		when(serverRequestWrapper.pathVariable(Constants.ELEMENT_ID)).thenReturn(Constants.ELEMENT_ID);
		when(serverRequestWrapper.pathVariable(Constants.COMMENT_ID)).thenReturn(Constants.COMMENT_ID);
		when(serverRequestWrapper.pathVariable(Constants.REPLY_ID)).thenReturn(Constants.REPLY_ID);
		when(replyService.deleteReplyById(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString())).thenReturn(Mono.empty());
		Mono<ServerResponse> serverResponse = replyHandler.deleteReplyById(serverRequestWrapper);
		serverResponse.subscribe(res -> {
			assertEquals(HttpStatus.OK, res.statusCode());
		});
	}

}
