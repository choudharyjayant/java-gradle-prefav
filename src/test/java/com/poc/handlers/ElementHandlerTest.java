package com.poc.handlers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;

import com.poc.dto.ElementDto;
import com.poc.models.Element;
import com.poc.services.IElementService;
import com.poc.utility.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class ElementHandlerTest {
	
    @InjectMocks
    private ElementHandler elementHandler;
    
    @Mock
    private IElementService elementService;

    @Mock
    private ServerRequestWrapper serverRequestWrapper;

    @Test
    public void getElementByProjectIdandSlateIdTest(){
        when(serverRequestWrapper.pathVariable(Constants.PROJECT_ID)).thenReturn(Constants.PROJECT_ID);
        when(serverRequestWrapper.pathVariable(Constants.SLATE_ID)).thenReturn(Constants.SLATE_ID);
        when(elementService.getElementByProjectIdandSlateId(ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn(Flux.just(new Element()));
        Mono<ServerResponse> serverResponse = elementHandler.getElementByProjectIdandSlateId(serverRequestWrapper);
        serverResponse.subscribe(res -> {
            assertEquals(HttpStatus.OK, res.statusCode());
        });
    }

    @Test
    public void addElementbyProjectandSlateIdTest(){
        when(serverRequestWrapper.pathVariable(Constants.PROJECT_ID)).thenReturn(Constants.PROJECT_ID);
        when(serverRequestWrapper.pathVariable(Constants.SLATE_ID)).thenReturn(Constants.SLATE_ID);
        when(serverRequestWrapper.bodyToMono(ElementDto.class)).thenReturn(Mono.just(new ElementDto()));     
        when(elementService.addElementByProjectandSlateId(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(Mono.just(new Element()));
        Mono<ServerResponse> serverResponse = elementHandler.addElementbyProjectandSlateId(serverRequestWrapper);
        serverResponse.subscribe(res -> {
            assertEquals(HttpStatus.OK, res.statusCode());
        });
    }

    @Test
    public void updateElementTest(){
        when(serverRequestWrapper.pathVariable(Constants.PROJECT_ID)).thenReturn(Constants.PROJECT_ID);
        when(serverRequestWrapper.pathVariable(Constants.SLATE_ID)).thenReturn(Constants.SLATE_ID);
        when(serverRequestWrapper.pathVariable(Constants.ELEMENT_ID)).thenReturn(Constants.ELEMENT_ID);
        when(serverRequestWrapper.bodyToMono(ElementDto.class)).thenReturn(Mono.just(new ElementDto()));
        when(elementService.updateElement(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(Mono.just(new Element()));
        Mono<ServerResponse> serverResponse = elementHandler.updateElement(serverRequestWrapper);
        serverResponse.subscribe(res -> {
            assertEquals(HttpStatus.OK, res.statusCode());
        });
    }

    @Test
    public void deleteElementTest(){
        when(serverRequestWrapper.pathVariable(Constants.PROJECT_ID)).thenReturn(Constants.PROJECT_ID);
        when(serverRequestWrapper.pathVariable(Constants.SLATE_ID)).thenReturn(Constants.SLATE_ID);
        when(serverRequestWrapper.pathVariable(Constants.ELEMENT_ID)).thenReturn(Constants.ELEMENT_ID);
        when(elementService.deleteElementById(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn(Mono.empty());
        Mono<ServerResponse> serverResponse = elementHandler.deleteElementById(serverRequestWrapper);
        serverResponse.subscribe(res -> {
            assertEquals(HttpStatus.OK, res.statusCode());
        });
    }
    
    @Test
    public void getElementsByElementId(){
        when(serverRequestWrapper.pathVariable(Constants.PROJECT_ID)).thenReturn(Constants.PROJECT_ID);
        when(serverRequestWrapper.pathVariable(Constants.SLATE_ID)).thenReturn(Constants.SLATE_ID);
        when(serverRequestWrapper.pathVariable(Constants.ELEMENT_ID)).thenReturn(Constants.ELEMENT_ID);
        when(elementService.getElementsByElementId(ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn(Mono.just(new Element()));
        Mono<ServerResponse> serverResponse = elementHandler.getElementsByElementId(serverRequestWrapper);
        serverResponse.subscribe(res -> {
            assertEquals(HttpStatus.OK, res.statusCode());
        });
    }

}
