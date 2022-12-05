package com.poc.handlers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;

import com.poc.dto.SlateDTO;
import com.poc.models.Slate;
import com.poc.services.ISlateService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RunWith(MockitoJUnitRunner.class)
public class SlateHandlerTest {
	
	@InjectMocks
	private SlateHandler slateHandler;
	
	@Mock
	private ISlateService slateService;
	
	@Mock
	private ServerRequestWrapper serverRequestWrapper;
	
	private static final String SLATE_ID="slateId";
	private static final String PROJECT_ID="gdg";
	
	
	@Test
	public void getSlateByIdTest() {
		when(serverRequestWrapper.pathVariable("slateId")).thenReturn(SLATE_ID);
		when(slateService.getSlateById(ArgumentMatchers.anyString())).thenReturn(Mono.just(new Slate()));
		Mono<ServerResponse> serverResponse = slateHandler.getSlateById(serverRequestWrapper);
		serverResponse.subscribe(res -> {
			assertEquals(HttpStatus.OK, res.statusCode());
		});
		
		Mockito.verify(serverRequestWrapper).pathVariable("slateId");
		Mockito.verify(slateService).getSlateById(ArgumentMatchers.anyString());
	}

	@Test
	public void deleteSlateByIdTest() {
		when(serverRequestWrapper.pathVariable("slateId")).thenReturn(SLATE_ID);
		when(slateService.deleteSlateById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());
		
		Mono<ServerResponse> serverResponse = slateHandler.deleteSlateById(serverRequestWrapper);
		
		serverResponse.subscribe(res -> {
			assertEquals(HttpStatus.OK, res.statusCode());
		

		});
		
		Mockito.verify(serverRequestWrapper).pathVariable("slateId");
		Mockito.verify(slateService).deleteSlateById(ArgumentMatchers.anyString());
	}
	 

	 @Test
	    public void updateSlateTest(){
	        when(serverRequestWrapper.pathVariable("projectId")).thenReturn(PROJECT_ID);
	        when(serverRequestWrapper.pathVariable("slateId")).thenReturn(SLATE_ID);
	        when(serverRequestWrapper.bodyToMono(SlateDTO.class)).thenReturn(Mono.just(new SlateDTO()));
	        when(slateService.updateSlate(ArgumentMatchers.any(),ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(Mono.just(new Slate()));
	        Mono<ServerResponse> serverResponse = slateHandler.updateSlate(serverRequestWrapper);
	        serverResponse.subscribe(res -> {
	            assertEquals(HttpStatus.CREATED, res.statusCode());
	        });
	        
	        Mockito.verify(serverRequestWrapper).pathVariable("projectId");
			Mockito.verify(serverRequestWrapper).pathVariable("slateId");
			Mockito.verify(serverRequestWrapper).bodyToMono(SlateDTO.class);
			Mockito.verify(slateService).updateSlate(ArgumentMatchers.any(),ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
	    }
	 @Test
	    public void addSlateTest(){
	        when(serverRequestWrapper.pathVariable("projectId")).thenReturn(PROJECT_ID);
	        when(serverRequestWrapper.bodyToMono(SlateDTO.class)).thenReturn(Mono.just(new SlateDTO()));
	        when(slateService.addSlate(ArgumentMatchers.any(),ArgumentMatchers.anyString())).thenReturn(Mono.just(new Slate()));
	        Mono<ServerResponse> serverResponse = slateHandler.addSlate(serverRequestWrapper);
	         serverResponse.subscribe(res -> {
	            assertEquals(HttpStatus.CREATED, res.statusCode());
	        });
	         Mockito.verify(serverRequestWrapper).pathVariable("projectId");
	         Mockito.verify(serverRequestWrapper).bodyToMono(SlateDTO.class);
			Mockito.verify(slateService).addSlate(ArgumentMatchers.any(),ArgumentMatchers.anyString());
	    }
	 
	 @Test
	    public void getSlatesByProjectIdTest() {
	        when(serverRequestWrapper.pathVariable("projectId")).thenReturn(PROJECT_ID);
	        when(slateService.getSlatesByProjectId(ArgumentMatchers.anyString())).thenReturn(Flux.just(new Slate()));
	        Mono<ServerResponse> serverResponse = slateHandler.getSlatesByProjectId(serverRequestWrapper);
	        serverResponse.subscribe(res -> {
	            assertEquals(HttpStatus.OK, res.statusCode());
	        });
	        Mockito.verify(serverRequestWrapper).pathVariable("projectId");
	        Mockito.verify(slateService).getSlatesByProjectId(ArgumentMatchers.anyString());
	        
	    }
}



