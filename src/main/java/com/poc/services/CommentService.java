package com.poc.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.dto.CommentDTO;
import com.poc.models.Comment;
import com.poc.repositories.CommentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommentService implements ICommentService {

	@Autowired
	private CommentRepository commentRepository;

	private String currDateAsString() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
	}

	private String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	@Override
	public Flux<Comment> getCommentsByElementId(String elementId) {
		return commentRepository.findAll().filter(comment -> comment.getElementWorkUrn().equals(elementId));
	}

	@Override
	public Mono<Comment> addCommentByElementId(String elementId, CommentDTO commentDTO) {
		Comment comment = new Comment();
		comment.setWorkUrn(uuid());
		comment.setEntityUrn(uuid());
		comment.setElementWorkUrn(uuid());
		comment.setSlateManifestUrn(uuid());
		comment.setText(commentDTO.getText());
		comment.setUserId(commentDTO.getUserId());
		comment.setCreatedBy(commentDTO.getCreatedBy());
		comment.setCreatedAt(currDateAsString());
		comment.setUpdatedBy(commentDTO.getUpdatedBy());
		comment.setUpdatedAt(currDateAsString());
		return commentRepository.save(comment);
	}

	@Override
	public Mono<Comment> editComment(String elementId, String commentId, CommentDTO commentDTO) {
		Mono<Comment> existingComment = commentRepository.findById(commentId);
		return existingComment.flatMap(comment -> {
			comment.setWorkUrn(commentId);
			comment.setElementWorkUrn(elementId);
			comment.setEntityUrn(uuid());
			comment.setSlateManifestUrn(uuid());
			comment.setText(commentDTO.getText());
			comment.setUserId(commentDTO.getUserId());
			comment.setCreatedBy(commentDTO.getCreatedBy());
			comment.setCreatedAt(currDateAsString());
			comment.setUpdatedBy(commentDTO.getUpdatedBy());
			comment.setUpdatedAt(currDateAsString());
			return commentRepository.save(comment);
		});
	}

	@Override
	public Mono<Void> deleteCommentById(String elementId, String commentId) {
		return commentRepository.deleteById(commentId);
	}
}
