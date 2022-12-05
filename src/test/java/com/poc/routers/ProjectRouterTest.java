package com.poc.routers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.handlers.ProjectHandler;

@RunWith(MockitoJUnitRunner.class)
public class ProjectRouterTest {

	@InjectMocks
	private ProjectRouter projectRouter;

	@Mock
	private ProjectHandler projectHandler;

	@Test
	public void projectRouteTest() {
		RouterFunction<ServerResponse> actualRouterFunction = projectRouter.projectRoute(projectHandler);
		assertTrue(actualRouterFunction.toString().contains("PUT"));
		assertTrue(actualRouterFunction.toString().contains("GET"));
		assertTrue(actualRouterFunction.toString().contains("DELETE"));
		assertTrue(actualRouterFunction.toString().contains("POST"));
	}
}
