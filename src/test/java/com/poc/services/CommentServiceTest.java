package com.poc.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.poc.dto.CommentDTO;
import com.poc.models.Comment;
import com.poc.repositories.CommentRepository;
import com.poc.utility.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    private static final String WORK_URN = "workUrn";
    private static final String ENTITY_URN = "entityUrn";
    private static final String ELEMENT_WORK_URN = "elementWorkUrn";
    private static final String SLATE_MANIFEST_URN = "slateManifestUrn";
    private static final String TEXT = "text";
    private static final String USER_ID = "userId";
    private static final String CREATED_BY = "createdBy";
    private static final String CREATED_AT = "createdAt";
    private static final String UPDATED_BY = "updatedBy";
    private static final String UPDATED_AT = "updatedAt";

    @Test
    public void getCommentsByElementIdTest() {
        Comment comment = comment();
        Mockito.when(commentRepository.findAll()).thenReturn(Flux.just(comment));
        Flux<Comment> result = commentService.getCommentsByElementId(Constants.ELEMENT_ID);
        System.out.println(result);
        assertEquals(true, result.toString().contains("FluxFilterFuseable"));
        assertNotNull(result);
    }

    @Test
    public void addCommentByElementIdTest() {
        Comment comment = comment();
        CommentDTO commentDTO = commentDTO();
        Mockito.when(commentRepository.save(ArgumentMatchers.any(Comment.class))).thenReturn(Mono.just(comment));
        Mono<Comment> commentMono = commentService.addCommentByElementId(Constants.ELEMENT_ID, commentDTO);
        StepVerifier.create(commentMono).expectNextMatches(res -> res.getText().contains(TEXT)).verifyComplete();
    }

    @Test
    public void editCommentTest() {
        Comment comment = comment();
        CommentDTO commentDTO = commentDTO();
        Mockito.when(commentRepository.findById(Constants.COMMENT_ID)).thenReturn(Mono.just(comment));
        Mockito.when(commentRepository.save(ArgumentMatchers.any(Comment.class))).thenReturn(Mono.just(comment));
        Mono<Comment> commentMono = commentService.editComment(Constants.ELEMENT_ID, Constants.COMMENT_ID, commentDTO);
        StepVerifier.create(commentMono).expectNextMatches(res -> res.getText().contains(TEXT)).verifyComplete();
    }

    @Test
    public void deleteCommentByIdTest() {
        Mockito.when(commentRepository.deleteById(Constants.COMMENT_ID)).thenReturn(Mono.empty());
        Mono<Void> result = commentService.deleteCommentById(Constants.ELEMENT_ID, Constants.COMMENT_ID);
        StepVerifier.create(result).expectNext().verifyComplete();
    }

    private Comment comment() {
        return new Comment(WORK_URN, ENTITY_URN, ELEMENT_WORK_URN, SLATE_MANIFEST_URN, TEXT, USER_ID, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT);
    }

    private CommentDTO commentDTO() {
        return new CommentDTO(WORK_URN, ENTITY_URN, ELEMENT_WORK_URN, SLATE_MANIFEST_URN, TEXT, USER_ID, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT);
    }

}
