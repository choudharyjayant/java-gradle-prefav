package com.poc.routers;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.handlers.ReplyHandler;

@RunWith(MockitoJUnitRunner.class)
public class ReplyRouterTest {

	@InjectMocks
	private ReplyRouter replyRouter;

	@Mock
	private ReplyHandler replyHandler;

	private static final String EXPECTED_ROUTER_FUNCTION = "Expected router function to be present.";

	@Test
	public void testReplyAPIs() {
		RouterFunction<ServerResponse> actualRouterFunction = replyRouter.replyAPIs(replyHandler);
		assertNotNull(EXPECTED_ROUTER_FUNCTION, actualRouterFunction);
	}
}
