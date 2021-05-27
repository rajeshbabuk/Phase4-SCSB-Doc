package org.recap.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.recap.BaseTestCaseUT;
import org.recap.ScsbCommonConstants;
import org.recap.model.deaccession.DeAccessionSolrRequest;
import org.recap.service.deaccession.DeAccessSolrDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by rajeshbabuk on 15/2/17.
 */
public class DeaccessionSolrControllerUT extends BaseTestCaseUT {

    @InjectMocks
    DeaccessionSolrController deaccessionSolrController;

    @Mock
    DeAccessSolrDocumentService deAccessSolrDocumentService;

    @Test
    public void deaccessionInSolr() throws Exception {
        DeAccessionSolrRequest deAccessionSolrRequest = new DeAccessionSolrRequest();
        deAccessionSolrRequest.setBibIds(Arrays.asList(1));
        deAccessionSolrRequest.setHoldingsIds(Arrays.asList(1));
        deAccessionSolrRequest.setItemIds(Arrays.asList(1));
        ResponseEntity responseEntity=deaccessionSolrController.deaccessionInSolr(deAccessionSolrRequest);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(ScsbCommonConstants.SUCCESS,responseEntity.getBody());
    }
}
