package com.poc.routers;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.poc.handlers.CommentHandler;

@RunWith(MockitoJUnitRunner.class)
public class CommentRouterTest {

    @InjectMocks
    private CommentRouter commentRouter;

    @Mock
    private CommentHandler commentHandler;

    private static final String EXPECTED_ROUTER_FUNCTION = "expected router function to be present";

    @Test
    public void testCommentAPIs() {
        RouterFunction<ServerResponse> actualRouterFunction = commentRouter.commentAPIs(commentHandler);
        assertNotNull(EXPECTED_ROUTER_FUNCTION, actualRouterFunction);
    }
}