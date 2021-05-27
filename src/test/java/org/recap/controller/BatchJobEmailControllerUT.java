package org.recap.controller;

import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.recap.BaseTestCaseUT;
import org.recap.ScsbCommonConstants;
import org.recap.model.camel.EmailPayLoad;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by rajeshbabuk on 20/4/17.
 */
public class BatchJobEmailControllerUT extends BaseTestCaseUT {

    @InjectMocks
    BatchJobEmailController batchJobEmailController;

    @Mock
    EmailPayLoad emailPayLoad;

    @Mock
    ProducerTemplate producerTemplate;

    @Test
    public void testBatchJobSendEmail() throws Exception {
        String response=batchJobEmailController.batchJobSendEmail(emailPayLoad);
        assertEquals(ScsbCommonConstants.SUCCESS,response);
    }
}
