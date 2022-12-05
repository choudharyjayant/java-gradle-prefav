package com.poc.routers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.handlers.ElementHandler;

@RunWith(MockitoJUnitRunner.class)
public class ElementRouterTest {
	
	@InjectMocks
	ElementRouter elementRouter;
	
	@Mock
	ElementHandler elementHandler;
	
	@Test
	public void RouterTest() {
		
		RouterFunction<ServerResponse>actualRouterFunction = elementRouter.elementAPIs();
		assertTrue(actualRouterFunction.toString().contains("POST"));
		assertTrue(actualRouterFunction.toString().contains("GET"));
		assertTrue(actualRouterFunction.toString().contains("PUT"));
		assertTrue(actualRouterFunction.toString().contains("DELETE"));
		
	}

}
