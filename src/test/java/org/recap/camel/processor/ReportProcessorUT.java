package org.recap.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.recap.model.jpa.ReportEntity;
import org.recap.repository.jpa.ReportDetailRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportProcessorUT extends BaseTestCaseUT {

    @InjectMocks
    ReportProcessor reportProcessor;

    @Mock
    ReportDetailRepository reportDetailRepository;

    @Mock
    Exchange exchange;

    @Mock
    Message value;

    @Test
    public void process() throws Exception {
        Mockito.when(exchange.getIn()).thenReturn(value);
        Mockito.when(value.getBody()).thenReturn(new ReportEntity());
        reportProcessor.process(exchange);
        assertTrue(true);
    }
    }
