package com.poc.handlers;

import com.poc.dto.CommentDTO;
import com.poc.models.Comment;
import com.poc.services.ICommentService;
import com.poc.utility.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentHandlerTest {
    @InjectMocks
    private CommentHandler commentHandler;

    @Mock
    private ICommentService commentService;

    @Mock
    private ServerRequestWrapper serverRequestWrapper;

    @Test
    public void getCommentsByElementIdTest() {
        when(serverRequestWrapper.pathVariable(Constants.ELEMENT_ID)).thenReturn(Constants.ELEMENT_ID);
        when(commentService.getCommentsByElementId(ArgumentMatchers.anyString())).thenReturn(Flux.just(new Comment()));
        Mono<ServerResponse> serverResponse = commentHandler.getCommentsByElementId(serverRequestWrapper);
        serverResponse.subscribe(res -> {
            assertEquals(HttpStatus.OK, res.statusCode());
        });
    }

    @Test
    public void addCommentByElementIdTest() {
        when(serverRequestWrapper.pathVariable(Constants.ELEMENT_ID)).thenReturn(Constants.ELEMENT_ID);
        when(serverRequestWrapper.bodyToMono(CommentDTO.class)).thenReturn(Mono.just(new CommentDTO()));
        when(commentService.addCommentByElementId(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(Mono.just(new Comment()));
        Mono<ServerResponse> serverResponse = commentHandler.addCommentByElementId(serverRequestWrapper);
        serverResponse.subscribe(res -> {
            assertEquals(HttpStatus.OK, res.statusCode());
        });
    }

    @Test
    public void editCommentTest() {
        when(serverRequestWrapper.pathVariable(Constants.ELEMENT_ID)).thenReturn(Constants.ELEMENT_ID);
        when(serverRequestWrapper.pathVariable(Constants.COMMENT_ID)).thenReturn(Constants.COMMENT_ID);
        when(serverRequestWrapper.bodyToMono(CommentDTO.class)).thenReturn(Mono.just(new CommentDTO()));
        when(commentService.editComment(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(Mono.just(new Comment()));
        Mono<ServerResponse> serverResponse = commentHandler.editComment(serverRequestWrapper);
        serverResponse.subscribe(res -> {
            assertEquals(HttpStatus.OK, res.statusCode());
        });
    }

    @Test
    public void deleteCommentTest() {
        when(serverRequestWrapper.pathVariable(Constants.ELEMENT_ID)).thenReturn(Constants.ELEMENT_ID);
        when(serverRequestWrapper.pathVariable(Constants.COMMENT_ID)).thenReturn(Constants.COMMENT_ID);
        when(commentService.deleteCommentById(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(Mono.empty());
        Mono<ServerResponse> serverResponse = commentHandler.deleteComment(serverRequestWrapper);
        serverResponse.subscribe(res -> {
            assertEquals(HttpStatus.OK, res.statusCode());
        });
    }

}
