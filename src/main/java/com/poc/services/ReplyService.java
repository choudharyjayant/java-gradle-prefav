package com.poc.services;

import com.poc.dto.ReplyDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReplyService {

	Flux<ReplyDto> getReplyByCommentId(String elementId, String commentId);

	Mono<ReplyDto> addReply(String elementId, String commentId, ReplyDto replyDto);

	Mono<ReplyDto> updateReply(String elementId, String commentId, String replyId, ReplyDto replyDto);

	Mono<Void> deleteReplyById(String replyId, String commentId, String elementId);

}
