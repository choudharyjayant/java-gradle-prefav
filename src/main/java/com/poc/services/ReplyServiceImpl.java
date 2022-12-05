package com.poc.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.dto.ReplyDto;
import com.poc.models.Reply;
import com.poc.repositories.ReplyRepository;
import com.poc.utility.AppUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyRepository replyRepository;

	private String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	private String date() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
	}

	@Override
	public Flux<ReplyDto> getReplyByCommentId(String elementId, String commentId) {
		return replyRepository.findAll().filter(reply -> reply.getCommentUrn().equals(commentId))
				.map(AppUtils::entityToDto);
	}

	@Override
	public Mono<ReplyDto> addReply(String elementId, String commentId, ReplyDto replyDto) {
		System.out.println("Add Reply Called");
		Reply reply = AppUtils.dtoToEntity(replyDto);
		reply.setWorkUrn(uuid());
		reply.setEntityUrn(uuid());
		reply.setCommentUrn(commentId);
		reply.setText(replyDto.getText());
		reply.setUserId(uuid());
		reply.setCreatedBy(replyDto.getCreatedAt());
		reply.setCreatedAt(date());
		reply.setUpdatedBy(replyDto.getUpdatedBy());
		reply.setUpdatedAt(date());
		return replyRepository.insert(reply).flatMap(entity -> Mono.just(AppUtils.entityToDto(entity)));

	}

	@Override
	public Mono<ReplyDto> updateReply(String elementId, String commentId, String replyId, ReplyDto replyDto) {
		Mono<Reply> existingReply = replyRepository.findById(replyId)
				.filter(oldReply -> oldReply.getCommentUrn().equals(commentId));
		return existingReply.flatMap(newReply -> {
			Reply reply = AppUtils.dtoToEntity(replyDto);
			reply.setWorkUrn(replyId);
			reply.setEntityUrn(replyDto.getEntityUrn());
			reply.setCommentUrn(replyDto.getCommentUrn());
			reply.setText(replyDto.getText());
			reply.setUserId(replyDto.getUserId());
			reply.setCreatedBy(replyDto.getCreatedBy());
			reply.setCreatedAt(replyDto.getCreatedAt());
			reply.setUpdatedBy(replyDto.getUpdatedBy());
			reply.setUpdatedAt(replyDto.getUpdatedAt());
			return replyRepository.save(reply).flatMap(entity -> Mono.just(AppUtils.entityToDto(entity)));
		});

	}

	@Override
	public Mono<Void> deleteReplyById(String elementId, String commentId, String replyId) {
		return replyRepository.deleteById(replyId);
	}

}
