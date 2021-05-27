package org.recap.controller;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.recap.BaseTestCaseUT;
import org.recap.BaseTestCaseUT4;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.matchingalgorithm.service.MatchingBibInfoDetailService;
import org.recap.model.solr.SolrIndexRequest;
import org.recap.util.DateUtil;
import org.recap.util.OngoingMatchingAlgorithmUtil;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by angelind on 13/6/17.
 */
public class OngoingMatchingAlgorithmJobControllerUT extends BaseTestCaseUT4 {

    @InjectMocks
    OngoingMatchingAlgorithmJobController ongoingMatchingAlgorithmJobController = new OngoingMatchingAlgorithmJobController();

    @Mock
    OngoingMatchingAlgorithmJobController ongoingMatchingAlgoJobController;

    @Mock
    OngoingMatchingAlgorithmUtil ongoingMatchingAlgorithmUtil;

    @Mock
    MatchingBibInfoDetailService matchingBibInfoDetailService;

    @Mock
    DateUtil dateUtil;

    @Mock
    QueryResponse queryResponse;

    private String batchSize="1000";

    @Test
    public void startOngoingMatchingAlgorithmJob() throws Exception {
        Date processDate = new Date();
        Date fromDate = dateUtil.getFromDate(processDate);
        SolrIndexRequest solrIndexRequest = new SolrIndexRequest();
        solrIndexRequest.setCreatedDate(processDate);
        SolrDocument solrDocument = new SolrDocument();
        SolrDocumentList solrDocumentList = new SolrDocumentList();
        solrDocumentList.add(solrDocument);
        List<Integer> bibIds = new ArrayList<>();
        Integer rows = Integer.valueOf(batchSize);
        Date date = solrIndexRequest.getCreatedDate();
        solrIndexRequest.setProcessType(ScsbCommonConstants.ONGOING_MATCHING_ALGORITHM_JOB);
        Mockito.when(ongoingMatchingAlgoJobController.getOngoingMatchingAlgorithmUtil()).thenReturn(ongoingMatchingAlgorithmUtil);
        Mockito.when(ongoingMatchingAlgoJobController.getDateUtil()).thenReturn(dateUtil);
        Mockito.when(ongoingMatchingAlgoJobController.getBatchSize()).thenReturn(batchSize);
        Mockito.when(ongoingMatchingAlgoJobController.getLogger()).thenCallRealMethod();
        Mockito.when(ongoingMatchingAlgorithmUtil.getFormattedDateString(fromDate)).thenReturn(processDate.toString());
        Mockito.when(ongoingMatchingAlgorithmUtil.fetchUpdatedRecordsAndStartProcess(dateUtil.getFromDate(date), rows)).thenReturn("Success");
        Mockito.when(ongoingMatchingAlgorithmUtil.fetchDataForOngoingMatchingBasedOnDate(processDate.toString(), Integer.valueOf(100), Integer.valueOf(0))).thenReturn(queryResponse);
        Mockito.when(queryResponse.getResults()).thenReturn(solrDocumentList);
        Mockito.when(ongoingMatchingAlgorithmUtil.processMatchingForBib(solrDocument, bibIds)).thenReturn(ScsbCommonConstants.SUCCESS);
        Mockito.when(ongoingMatchingAlgoJobController.startMatchingAlgorithmJob(solrIndexRequest)).thenCallRealMethod();
        Mockito.when(ongoingMatchingAlgorithmUtil.processOngoingMatchingAlgorithm(solrDocumentList, new ArrayList<>())).thenCallRealMethod();
        String status = ongoingMatchingAlgoJobController.startMatchingAlgorithmJob(solrIndexRequest);
        assertTrue(status.contains(ScsbCommonConstants.SUCCESS));
        Mockito.when(ongoingMatchingAlgoJobController.getOngoingMatchingAlgorithmUtil()).thenCallRealMethod();
        assertNotEquals(ongoingMatchingAlgorithmUtil, ongoingMatchingAlgoJobController.getOngoingMatchingAlgorithmUtil());
        Mockito.when(ongoingMatchingAlgoJobController.getDateUtil()).thenCallRealMethod();
        assertNotEquals(dateUtil, ongoingMatchingAlgoJobController.getDateUtil());
    }

    @Test
    public void startJobToPopulateDataDumpMatchingBibs() throws Exception {
        Date processDate = new Date();
        Date fromDate = dateUtil.getFromDate(processDate);
        Date toDate = dateUtil.getToDate(processDate);
        SolrIndexRequest solrIndexRequest = new SolrIndexRequest();
        solrIndexRequest.setCreatedDate(processDate);
        solrIndexRequest.setProcessType(ScsbConstants.POPULATE_DATA_FOR_DATA_DUMP_JOB);
        Mockito.when(ongoingMatchingAlgoJobController.getMatchingBibInfoDetailService()).thenReturn(matchingBibInfoDetailService);
        Mockito.when(ongoingMatchingAlgoJobController.getDateUtil()).thenReturn(dateUtil);
        Mockito.when(ongoingMatchingAlgoJobController.getLogger()).thenCallRealMethod();
        Mockito.when(matchingBibInfoDetailService.populateMatchingBibInfo(fromDate, toDate)).thenReturn(ScsbCommonConstants.SUCCESS);
        Mockito.when(ongoingMatchingAlgoJobController.getBatchSize()).thenReturn(batchSize);
        Mockito.when(ongoingMatchingAlgoJobController.startMatchingAlgorithmJob(solrIndexRequest)).thenCallRealMethod();
        String status = ongoingMatchingAlgoJobController.startMatchingAlgorithmJob(solrIndexRequest);
        assertTrue(status.contains(ScsbCommonConstants.SUCCESS));
        Mockito.when(ongoingMatchingAlgoJobController.getMatchingBibInfoDetailService()).thenCallRealMethod();
        assertNotEquals(matchingBibInfoDetailService, ongoingMatchingAlgoJobController.getMatchingBibInfoDetailService());
    }

    @Test
    public void startMatchingAlgorithmJobException() throws Exception {
        Date processDate = new Date();
        Date fromDate = dateUtil.getFromDate(processDate);
        Date toDate = dateUtil.getToDate(processDate);
        SolrIndexRequest solrIndexRequest = new SolrIndexRequest();
        solrIndexRequest.setCreatedDate(processDate);
        solrIndexRequest.setProcessType(ScsbConstants.POPULATE_DATA_FOR_DATA_DUMP_JOB);
        Mockito.when(ongoingMatchingAlgoJobController.getMatchingBibInfoDetailService()).thenReturn(matchingBibInfoDetailService);
        Mockito.when(ongoingMatchingAlgoJobController.getDateUtil()).thenReturn(dateUtil);
        Mockito.when(ongoingMatchingAlgoJobController.getLogger()).thenCallRealMethod();
        Mockito.when(matchingBibInfoDetailService.populateMatchingBibInfo(fromDate, toDate)).thenThrow(NullPointerException.class);
        Mockito.when(ongoingMatchingAlgoJobController.getBatchSize()).thenReturn(batchSize);
        Mockito.when(ongoingMatchingAlgoJobController.startMatchingAlgorithmJob(solrIndexRequest)).thenCallRealMethod();
        String status = ongoingMatchingAlgoJobController.startMatchingAlgorithmJob(solrIndexRequest);
        assertEquals("",status);
     }

    @Test
    public void matchingJob(){
        Model model= PowerMockito.mock(Model.class);
        Mockito.when(ongoingMatchingAlgoJobController.matchingJob(model)).thenCallRealMethod();
        String job=ongoingMatchingAlgoJobController.matchingJob(model);
        assertEquals("ongoingMatchingJob",job);
    }

    @Test
    public void checkGetterServices(){
        Mockito.when(ongoingMatchingAlgoJobController.getMatchingBibInfoDetailService()).thenCallRealMethod();
        Mockito.when(ongoingMatchingAlgoJobController.getOngoingMatchingAlgorithmUtil()).thenCallRealMethod();
        Mockito.when(ongoingMatchingAlgoJobController.getBatchSize()).thenCallRealMethod();
        Mockito.when(ongoingMatchingAlgoJobController.getDateUtil()).thenCallRealMethod();
        assertNotEquals(matchingBibInfoDetailService,ongoingMatchingAlgoJobController.getMatchingBibInfoDetailService());
        assertNotEquals(ongoingMatchingAlgorithmUtil,ongoingMatchingAlgoJobController.getOngoingMatchingAlgorithmUtil());
        assertNotEquals(batchSize,ongoingMatchingAlgoJobController.getBatchSize());
        assertNotEquals(dateUtil,ongoingMatchingAlgoJobController.getDateUtil());
    }

}