package org.recap.camel.processor;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.spi.RouteController;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.recap.BaseTestCaseUT4;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertTrue;

public class StopRouteProcessorUT extends BaseTestCaseUT4 {

    @InjectMocks
    StopRouteProcessor stopRouteProcessor;

    @Mock
    Exchange exchange;

    @Mock
    CamelContext camelContext;

    @Mock
    RouteController routeController;

    @Test
    public void process() throws Exception {
        ReflectionTestUtils.setField(stopRouteProcessor,"routeId","1");
        Mockito.when(exchange.getContext()).thenReturn(camelContext);
        Mockito.when(camelContext.getRouteController()).thenReturn(routeController);
        Mockito.doNothing().when(routeController).stopRoute(Mockito.anyString());
        stopRouteProcessor.process(exchange);
        assertTrue(true);
    }

    @Test
    public void processException() throws Exception {
         stopRouteProcessor.process(exchange);
        assertTrue(true);
    }
}
