package com.poc.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.poc.dto.ReplyDto;
import com.poc.models.Reply;
import com.poc.repositories.ReplyRepository;
import com.poc.utility.AppUtils;
import com.poc.utility.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class ReplyServiceTests {

	@InjectMocks
	private ReplyServiceImpl replyService;

	@Mock
	private ReplyRepository replyRepository;

	@Mock
	private AppUtils appUtils;

	private Reply reply() {
		return new Reply("1", "1", "1", "firstTest", "testUser", "Bilal", "12PM", "Ashraf", "4PM");
	}

	private ReplyDto replyDto() {
		return new ReplyDto("1", "1", "1", "firstTest", "testUser", "Bilal", "12PM", "Ashraf", "4PM");
	}

	@Test
	public void getReplyTest() {
		Reply reply = reply();
		ReplyDto replyDto = replyDto();
		when(replyRepository.findAll()).thenReturn(Flux.just(reply));
		try (MockedStatic<AppUtils> utilities = Mockito.mockStatic(AppUtils.class)) {
			utilities.when(() -> AppUtils.entityToDto(reply)).thenReturn(replyDto);
		}
		Flux<ReplyDto> resultFlux = replyService.getReplyByCommentId(Constants.ELEMENT_ID, Constants.COMMENT_ID);
		assertEquals(true, resultFlux.toString().contains("FluxMapFuseable"));
		assertNotNull(resultFlux);
	}

	@Test
	public void addReplyTest() {
		Reply reply = reply();
		ReplyDto replyDto = replyDto();
		try (MockedStatic<AppUtils> utilities = Mockito.mockStatic(AppUtils.class)) {
			utilities.when(() -> AppUtils.dtoToEntity(replyDto)).thenReturn(reply);
		}
		Mockito.when(replyRepository.insert(any(Reply.class))).thenReturn(Mono.just(reply));
		try (MockedStatic<AppUtils> utilities = Mockito.mockStatic(AppUtils.class)) {
			utilities.when(() -> AppUtils.entityToDto(reply)).thenReturn(replyDto);
		}
		Mono<ReplyDto> resultMono = replyService.addReply(Constants.ELEMENT_ID, Constants.COMMENT_ID, replyDto);
		resultMono.subscribe(res -> {
			assertEquals("1", res.getWorkUrn());
			assertEquals("1", res.getEntityUrn());
			assertEquals("1", res.getCommentUrn());
			assertEquals("firstTest", res.getText());
			assertEquals("testUser", res.getUserId());
			assertEquals("Bilal", res.getCreatedBy());
			assertEquals("12PM", res.getCreatedAt());
			assertEquals("Ashraf", res.getUpdatedBy());
			assertEquals("4PM", res.getUpdatedAt());
		});
	}

	@Test
	public void updateReplyTest() {
		Mono<Reply> existingReplyMono = Mono
				.just(new Reply("1", "1", "commentId", "firstTest", "testUser", "Bilal", "12PM", "Ashraf", "4PM"));
		ReplyDto replyDto = new ReplyDto("1", "1", "1", "UpdateTest", "testUser", "Bilal", "12PM", "Ashraf", "4PM");
		when(replyRepository.findById(Constants.REPLY_ID)).thenReturn(existingReplyMono);
		Reply existingReply = reply();
		try (MockedStatic<AppUtils> utilities = Mockito.mockStatic(AppUtils.class)) {
			utilities.when(() -> AppUtils.dtoToEntity(replyDto)).thenReturn(existingReply);
		}
		Reply newReply = new Reply("1", "1", "1", "UpdateTest", "testUser", "Bilal", "12PM", "Ashraf", "4PM");
		when(replyRepository.save(any(Reply.class))).thenReturn(Mono.just(newReply));
		try (MockedStatic<AppUtils> utilities = Mockito.mockStatic(AppUtils.class)) {
			utilities.when(() -> AppUtils.entityToDto(existingReply)).thenReturn(replyDto);
		}
		Mono<ReplyDto> resultMono = replyService.updateReply(Constants.ELEMENT_ID, Constants.COMMENT_ID, Constants.REPLY_ID, replyDto);
		resultMono.subscribe(res -> {
			assertEquals("1", res.getWorkUrn());
			assertEquals("1", res.getEntityUrn());
			assertEquals("1", res.getCommentUrn());
			assertEquals("UpdateTest", res.getText());
			assertEquals("testUser", res.getUserId());
			assertEquals("Bilal", res.getCreatedBy());
			assertEquals("12PM", res.getCreatedAt());
			assertEquals("Ashraf", res.getUpdatedBy());
			assertEquals("4PM", res.getUpdatedAt());
		});
	}

	@Test
	public void deleteReplyTest() {
		Mockito.when(replyService.deleteReplyById(Constants.ELEMENT_ID, Constants.COMMENT_ID, Constants.REPLY_ID)).thenReturn(Mono.empty());
		StepVerifier.create(replyService.deleteReplyById(Constants.ELEMENT_ID, Constants.COMMENT_ID, Constants.REPLY_ID)).expectComplete()
				.verify();
	}

}
