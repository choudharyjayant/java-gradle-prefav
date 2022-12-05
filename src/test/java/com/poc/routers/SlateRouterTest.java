package com.poc.routers;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.handlers.SlateHandler;

@RunWith(MockitoJUnitRunner.class)
public class SlateRouterTest {
	
	@InjectMocks
    private SlateRouter slateRouter;

    @Mock
    private SlateHandler slateHandler;
    
    private static final String EXPECTED_ROUTER_FUNCTION = "expected router function to be present";

    @Test
    public void testGetResponses() {
        RouterFunction<ServerResponse> actualRouterFunction = slateRouter.getResponses(slateHandler);
        assertNotNull(EXPECTED_ROUTER_FUNCTION, actualRouterFunction);
    }

}
