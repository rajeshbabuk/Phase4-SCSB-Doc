package org.recap.controller;

import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.BaseTestCaseUT;
import org.recap.BaseTestCaseUT4;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.executors.MatchingBibItemIndexExecutorService;
import org.recap.matchingalgorithm.MatchingCounter;
import org.recap.matchingalgorithm.service.MatchingAlgorithmHelperService;
import org.recap.matchingalgorithm.service.MatchingAlgorithmUpdateCGDService;
import org.recap.matchingalgorithm.service.MatchingBibInfoDetailService;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.ReportDataEntity;
import org.recap.report.ReportGenerator;
import org.recap.repository.jpa.BibliographicDetailsRepository;
import org.recap.repository.jpa.CollectionGroupDetailsRepository;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.recap.repository.jpa.ItemChangeLogDetailsRepository;
import org.recap.repository.jpa.ItemDetailsRepository;
import org.recap.repository.jpa.MatchingBibInfoDetailRepository;
import org.recap.repository.jpa.ReportDataDetailsRepository;
import org.recap.repository.jpa.ReportDetailRepository;
import org.recap.service.ActiveMqQueuesInfo;
import org.recap.util.CommonUtil;
import org.recap.util.MatchingAlgorithmUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.recap.ScsbConstants.*;
import static org.recap.ScsbConstants.MATCHING_COUNTER_UPDATED_OPEN;


/**
 * Created by hemalathas on 1/8/16.
 */
public class MatchingAlgorithmControllerUT extends BaseTestCaseUT4 {

    Logger logger = LoggerFactory.getLogger(MatchingAlgorithmControllerUT.class);

    @Mock
    MatchingAlgorithmController matchingAlgoController;

    @Mock
    ReportGenerator reportGenerator;

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    @Mock
    MatchingAlgorithmHelperService matchingAlgorithmHelperService;

    @Mock
    MatchingAlgorithmUpdateCGDService matchingAlgorithmUpdateCGDService;

    @Mock
    MatchingBibItemIndexExecutorService matchingBibItemIndexExecutorService;

    @Mock
    MatchingBibInfoDetailService matchingBibInfoDetailService;

    @Mock
    MatchingAlgorithmUtil matchingAlgorithmUtil;

    @Mock
    ReportDataDetailsRepository reportDataDetailsRepository;

    @Mock
    BibliographicDetailsRepository mockedBibliographicDetailsRepository;

    @Mock
    ProducerTemplate producerTemplate;

    @Mock
    ItemChangeLogDetailsRepository itemChangeLogDetailsRepository;

    @Mock
    CollectionGroupDetailsRepository collectionGroupDetailsRepository;

    @Mock
    ItemDetailsRepository itemDetailsRepository;

    @Mock
    ReportDetailRepository reportDetailRepository;

    @Mock
    ActiveMqQueuesInfo activeMqQueuesInfo;

    @Mock
    MatchingBibInfoDetailRepository matchingBibInfoDetailRepository;

    @Mock
    CommonUtil commonUtil;

    @Mock
    InstitutionDetailsRepository institutionDetailsRepository;

    @Mock
    MatchingCounter matchingCounter;

    @Mock
    private Map collectionGroupMap;
    private Map institutionMap;

    private Integer batchSize = 10000;

    private int pageNum = 1;

    List<String> scsbInstitutions=Arrays.asList("HTC");

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        Map<String,Integer> cgdCounterMap=new HashMap<>();
        cgdCounterMap.put(MATCHING_COUNTER_SHARED,1);
        cgdCounterMap.put(MATCHING_COUNTER_OPEN,1);
        cgdCounterMap.put(MATCHING_COUNTER_UPDATED_SHARED,0);
        cgdCounterMap.put(MATCHING_COUNTER_UPDATED_OPEN,0);
        List<String> institutions= Arrays.asList("PUL","CUL","NYPL","HL","UC");
        Map<String, Map<String, Integer>> institutionCounterMap=new HashMap<>();
        for (String institution : institutions) {
            institutionCounterMap.put(institution,cgdCounterMap);
        }
        ReflectionTestUtils.setField(matchingAlgoController,"commonUtil",commonUtil);
        ReflectionTestUtils.setField(matchingCounter,"scsbInstitutions",scsbInstitutions);
        ReflectionTestUtils.setField(commonUtil,"supportInstitution","HTC");
        ReflectionTestUtils.setField(matchingCounter,"institutionCounterMap",institutionCounterMap);
        ReflectionTestUtils.setField(commonUtil,"institutionDetailsRepository",institutionDetailsRepository);
        Mockito.when(matchingAlgoController.getLogger()).thenCallRealMethod();
        Mockito.when(matchingAlgoController.getMatchingAlgoBatchSize()).thenReturn(String.valueOf(batchSize));
        Mockito.when(matchingAlgoController.getMatchingAlgoBatchSize()).thenReturn(String.valueOf(batchSize));
    }

    @Test
    public void matchingAlgorithmFullTest() throws Exception {
        Date matchingAlgoDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String matchingAlgoDateString = sdf.format(matchingAlgoDate);
        Mockito.when(matchingAlgoController.matchingAlgorithmFindMatchingAndReports()).thenReturn(ScsbConstants.STATUS_DONE);
        Mockito.when(matchingAlgoController.updateMonographCGDInDB()).thenReturn(ScsbConstants.STATUS_DONE);
        Mockito.when(matchingAlgoController.updateSerialCGDInDB()).thenReturn(ScsbConstants.STATUS_DONE);
        Mockito.when(matchingAlgoController.updateMvmCGDInDB()).thenReturn(ScsbConstants.STATUS_DONE);
        Mockito.when(matchingAlgoController.updateCGDInSolr(matchingAlgoDateString)).thenReturn(ScsbConstants.STATUS_DONE);
        Mockito.when(matchingAlgoController.matchingAlgorithmFull(matchingAlgoDateString)).thenCallRealMethod();
        String response = matchingAlgoController.matchingAlgorithmFull(matchingAlgoDateString);
        assertTrue(response.contains(ScsbConstants.STATUS_DONE));
    }

    @Test
    public void matchingAlgorithmFullTestException() throws Exception {
        Date matchingAlgoDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String matchingAlgoDateString = sdf.format(matchingAlgoDate);
        Mockito.when(matchingAlgoController.matchingAlgorithmFindMatchingAndReports()).thenReturn(ScsbConstants.STATUS_DONE);
        Mockito.when(matchingAlgoController.updateMonographCGDInDB()).thenReturn(ScsbConstants.STATUS_DONE);
        Mockito.when(matchingAlgoController.updateSerialCGDInDB()).thenReturn(ScsbConstants.STATUS_DONE);
        Mockito.when(matchingAlgoController.updateMvmCGDInDB()).thenReturn(ScsbConstants.STATUS_DONE);
        Mockito.when(matchingAlgoController.updateCGDInSolr(matchingAlgoDateString)).thenThrow(NullPointerException.class);
        Mockito.when(matchingAlgoController.matchingAlgorithmFull(matchingAlgoDateString)).thenCallRealMethod();
        String response = matchingAlgoController.matchingAlgorithmFull(matchingAlgoDateString);
        assertTrue(response.contains(ScsbConstants.STATUS_FAILED));
    }

    @Test
    public void matchingAlgorithmFindMatchingAndReportsTest() throws Exception {
        Mockito.when(matchingAlgoController.getMatchingAlgorithmHelperService()).thenReturn(matchingAlgorithmHelperService);
        Mockito.when(matchingAlgorithmHelperService.findMatchingAndPopulateMatchPointsEntities()).thenReturn(Long.valueOf(10));
        Mockito.when(matchingAlgorithmHelperService.populateMatchingBibEntities()).thenReturn(Long.valueOf(10));
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_OCLC, ScsbCommonConstants.MATCH_POINT_FIELD_ISBN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_OCLC, ScsbCommonConstants.MATCH_POINT_FIELD_ISSN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_OCLC, ScsbCommonConstants.MATCH_POINT_FIELD_LCCN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_ISBN, ScsbCommonConstants.MATCH_POINT_FIELD_ISSN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_ISBN, ScsbCommonConstants.MATCH_POINT_FIELD_LCCN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_ISSN, ScsbCommonConstants.MATCH_POINT_FIELD_LCCN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForSingleMatch(batchSize, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgoController.matchingAlgorithmFindMatchingAndReports()).thenCallRealMethod();
        String response = matchingAlgoController.matchingAlgorithmFindMatchingAndReports();
        assertTrue(response.contains(ScsbConstants.STATUS_DONE));
    }

    @Test
    public void matchingAlgorithmFindMatchingAndReportsTestException() throws Exception {
        Mockito.when(matchingAlgoController.getMatchingAlgorithmHelperService()).thenThrow(NullPointerException.class);
        Mockito.when(matchingAlgorithmHelperService.findMatchingAndPopulateMatchPointsEntities()).thenReturn(Long.valueOf(10));
        Mockito.when(matchingAlgorithmHelperService.populateMatchingBibEntities()).thenReturn(Long.valueOf(10));
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_OCLC, ScsbCommonConstants.MATCH_POINT_FIELD_ISBN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_OCLC, ScsbCommonConstants.MATCH_POINT_FIELD_ISSN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_OCLC, ScsbCommonConstants.MATCH_POINT_FIELD_LCCN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_ISBN, ScsbCommonConstants.MATCH_POINT_FIELD_ISSN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_ISBN, ScsbCommonConstants.MATCH_POINT_FIELD_LCCN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_ISSN, ScsbCommonConstants.MATCH_POINT_FIELD_LCCN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForSingleMatch(batchSize, getStringIntegerMap())).thenThrow(NullPointerException.class);
        Mockito.when(matchingAlgoController.matchingAlgorithmFindMatchingAndReports()).thenCallRealMethod();
        String response = matchingAlgoController.matchingAlgorithmFindMatchingAndReports();
        assertTrue(response.contains(ScsbConstants.STATUS_FAILED));
    }


    private Map<String, Integer> getStringIntegerMap() {
        Map<String, Integer> matchingAlgoMap = new HashMap<>();
        matchingAlgoMap.put("pulMatchingCount", 1);
        matchingAlgoMap.put("culMatchingCount", 2);
        matchingAlgoMap.put("nyplMatchingCount", 3);
        return matchingAlgoMap;
    }

    @Test
    public void matchingAlgorithmOnlyReports() throws Exception {
        Mockito.when(matchingAlgoController.getMatchingAlgorithmHelperService()).thenReturn(matchingAlgorithmHelperService);
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_OCLC, ScsbCommonConstants.MATCH_POINT_FIELD_ISBN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_OCLC, ScsbCommonConstants.MATCH_POINT_FIELD_ISSN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_OCLC, ScsbCommonConstants.MATCH_POINT_FIELD_LCCN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_ISBN, ScsbCommonConstants.MATCH_POINT_FIELD_ISSN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(commonUtil.findAllInstitutionCodesExceptSupportInstitution()).thenCallRealMethod();
        List<String> allInstitutionCodeExceptSupportInstitution=Arrays.asList(ScsbCommonConstants.COLUMBIA,ScsbCommonConstants.PRINCETON,ScsbCommonConstants.NYPL);
        Mockito.when(institutionDetailsRepository.findAllInstitutionCodesExceptSupportInstitution(Mockito.anyString())).thenReturn(allInstitutionCodeExceptSupportInstitution);
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_ISBN, ScsbCommonConstants.MATCH_POINT_FIELD_LCCN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_ISSN, ScsbCommonConstants.MATCH_POINT_FIELD_LCCN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForSingleMatch(batchSize, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgoController.matchingAlgorithmOnlyReports()).thenCallRealMethod();
        String response = matchingAlgoController.matchingAlgorithmOnlyReports();
        assertTrue(response.contains(ScsbConstants.STATUS_DONE));
    }

    @Test
    public void matchingAlgorithmOnlyReportsException() throws Exception {
        Mockito.when(commonUtil.findAllInstitutionCodesExceptSupportInstitution()).thenThrow(NullPointerException.class);
        Mockito.when(matchingAlgoController.getMatchingAlgorithmHelperService()).thenReturn(matchingAlgorithmHelperService);
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_OCLC, ScsbCommonConstants.MATCH_POINT_FIELD_ISBN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_OCLC, ScsbCommonConstants.MATCH_POINT_FIELD_ISSN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_OCLC, ScsbCommonConstants.MATCH_POINT_FIELD_LCCN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_ISBN, ScsbCommonConstants.MATCH_POINT_FIELD_ISSN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_ISBN, ScsbCommonConstants.MATCH_POINT_FIELD_LCCN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForMatchPoints(batchSize, ScsbCommonConstants.MATCH_POINT_FIELD_ISSN, ScsbCommonConstants.MATCH_POINT_FIELD_LCCN, getStringIntegerMap())).thenReturn(getStringIntegerMap());
        Mockito.when(matchingAlgorithmHelperService.populateReportsForSingleMatch(batchSize, getStringIntegerMap())).thenThrow(NullPointerException.class);
        Mockito.when(matchingAlgoController.matchingAlgorithmOnlyReports()).thenCallRealMethod();
        String response = matchingAlgoController.matchingAlgorithmOnlyReports();
        assertTrue(response.contains(ScsbConstants.STATUS_FAILED));
    }

    @Test
    public void updateMonographCGDInDB() throws Exception {
        Mockito.when(matchingAlgoController.getMatchingAlgorithmUpdateCGDService()).thenReturn(matchingAlgorithmUpdateCGDService);
        Mockito.doNothing().when(matchingAlgorithmUpdateCGDService).updateCGDProcessForMonographs(batchSize);
        Mockito.when(matchingAlgoController.updateMonographCGDInDB()).thenCallRealMethod();
        String response = matchingAlgoController.updateMonographCGDInDB();
        assertTrue(response.contains(ScsbConstants.STATUS_DONE));
    }

    @Test
    public void updateMonographCGDInDBException() throws Exception {
        Mockito.when(matchingAlgoController.getMatchingAlgorithmUpdateCGDService()).thenReturn(matchingAlgorithmUpdateCGDService);
        Mockito.doThrow(NullPointerException.class).when(matchingAlgorithmUpdateCGDService).updateCGDProcessForMonographs(batchSize);
        Mockito.when(matchingAlgoController.updateMonographCGDInDB()).thenCallRealMethod();
        String response = matchingAlgoController.updateMonographCGDInDB();
        assertTrue(response.contains(ScsbConstants.STATUS_FAILED));
    }



    @Test
    public void updateSerialCGDInDB() throws Exception {
        Mockito.when(matchingAlgoController.getMatchingAlgorithmUpdateCGDService()).thenReturn(matchingAlgorithmUpdateCGDService);
        Mockito.doNothing().when(matchingAlgorithmUpdateCGDService).updateCGDProcessForSerials(batchSize);
        Mockito.when(matchingAlgoController.updateSerialCGDInDB()).thenCallRealMethod();
        String response = matchingAlgoController.updateSerialCGDInDB();
        assertTrue(response.contains(ScsbConstants.STATUS_DONE));
    }

    @Test
    public void updateSerialCGDInDBException() throws Exception {
        Mockito.when(matchingAlgoController.getMatchingAlgorithmUpdateCGDService()).thenReturn(matchingAlgorithmUpdateCGDService);
        Mockito.doThrow(NullPointerException.class).when(matchingAlgorithmUpdateCGDService).updateCGDProcessForSerials(batchSize);
        Mockito.when(matchingAlgoController.updateSerialCGDInDB()).thenCallRealMethod();
        String response = matchingAlgoController.updateSerialCGDInDB();
        assertTrue(response.contains(ScsbConstants.STATUS_FAILED));
    }


    @Test
    public void updateMvmCGDInDB() throws Exception {
        Mockito.when(matchingAlgoController.getMatchingAlgorithmUpdateCGDService()).thenReturn(matchingAlgorithmUpdateCGDService);
        Mockito.doNothing().when(matchingAlgorithmUpdateCGDService).updateCGDProcessForMVMs(batchSize);
        Mockito.when(matchingAlgoController.updateMvmCGDInDB()).thenCallRealMethod();
        String response = matchingAlgoController.updateMvmCGDInDB();
        assertTrue(response.contains(ScsbConstants.STATUS_DONE));
    }

    @Test
    public void updateMvmCGDInDBException() throws Exception {
        Mockito.when(matchingAlgoController.getMatchingAlgorithmUpdateCGDService()).thenReturn(matchingAlgorithmUpdateCGDService);
        Mockito.doThrow(NullPointerException.class).when(matchingAlgorithmUpdateCGDService).updateCGDProcessForMVMs(batchSize);
        Mockito.when(matchingAlgoController.updateMvmCGDInDB()).thenCallRealMethod();
        String response = matchingAlgoController.updateMvmCGDInDB();
        assertTrue(response.contains(ScsbConstants.STATUS_FAILED));
    }



    @Test
    public void updateCGDInSolr() throws Exception {
        Date matchingAlgoDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String matchingAlgoDateString = sdf.format(matchingAlgoDate);
        Date updatedDate = new Date();
        try {
            updatedDate = sdf.parse(matchingAlgoDateString);
        } catch (ParseException e) {
            logger.error("Exception while parsing Date : " + e.getMessage());
        }
        Mockito.when(matchingAlgoController.getMatchingBibItemIndexExecutorService()).thenReturn(matchingBibItemIndexExecutorService);
        Mockito.when(matchingBibItemIndexExecutorService.indexingForMatchingAlgorithm(ScsbConstants.INITIAL_MATCHING_OPERATION_TYPE, updatedDate)).thenReturn(1);
        Mockito.when(matchingAlgoController.updateCGDInSolr(matchingAlgoDateString)).thenCallRealMethod();
        String response = matchingAlgoController.updateCGDInSolr(matchingAlgoDateString);
        assertTrue(response.contains(ScsbConstants.STATUS_DONE));
    }

    @Test
    public void updateCGDInSolrException() throws Exception {
        Date matchingAlgoDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String matchingAlgoDateString = sdf.format(matchingAlgoDate);
        Date updatedDate = new Date();
        try {
            updatedDate = sdf.parse(matchingAlgoDateString);
        } catch (ParseException e) {
            logger.error("Exception while parsing Date : " + e.getMessage());
        }
        Mockito.when(matchingAlgoController.getMatchingBibItemIndexExecutorService()).thenReturn(matchingBibItemIndexExecutorService);
        Mockito.when(matchingBibItemIndexExecutorService.indexingForMatchingAlgorithm(ScsbConstants.INITIAL_MATCHING_OPERATION_TYPE, updatedDate)).thenThrow(NullPointerException.class);
        Mockito.when(matchingAlgoController.updateCGDInSolr(matchingAlgoDateString)).thenCallRealMethod();
        String response = matchingAlgoController.updateCGDInSolr(matchingAlgoDateString);
        assertTrue(response.contains(ScsbConstants.STATUS_FAILED));
    }

    @Test
    public void updateCGDInSolrParseException() throws Exception {
        Date matchingAlgoDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String matchingAlgoDateString = sdf.format(matchingAlgoDate);
        Date updatedDate = new Date();
        try {
            updatedDate = sdf.parse(matchingAlgoDateString);
        } catch (ParseException e) {
            logger.error("Exception while parsing Date : " + e.getMessage());
        }
        Mockito.when(matchingAlgoController.getMatchingBibItemIndexExecutorService()).thenReturn(matchingBibItemIndexExecutorService);
        Mockito.when(matchingBibItemIndexExecutorService.indexingForMatchingAlgorithm(ScsbConstants.INITIAL_MATCHING_OPERATION_TYPE, updatedDate)).thenThrow(NullPointerException.class);
        Mockito.when(matchingAlgoController.updateCGDInSolr(new Date().toString())).thenCallRealMethod();
        String response = matchingAlgoController.updateCGDInSolr(new Date().toString());
        assertTrue(response.contains(ScsbConstants.STATUS_DONE));
    }

    @Test
    public void testPopulateDataForDataDump() throws Exception {
        Mockito.when(matchingAlgoController.getMatchingBibInfoDetailService()).thenReturn(matchingBibInfoDetailService);
        Mockito.when(matchingBibInfoDetailService.populateMatchingBibInfo()).thenReturn(ScsbCommonConstants.SUCCESS);
        Mockito.when(matchingAlgoController.populateDataForDataDump()).thenCallRealMethod();
        String response = matchingAlgoController.populateDataForDataDump();
        assertTrue(response.contains(ScsbCommonConstants.SUCCESS));
    }

    @Test
    public void testPopulateDataForDataDumpException() throws Exception {
        Mockito.when(matchingAlgoController.getMatchingBibInfoDetailService()).thenReturn(matchingBibInfoDetailService);
        Mockito.when(matchingBibInfoDetailService.populateMatchingBibInfo()).thenThrow(NullPointerException.class);
        Mockito.when(matchingAlgoController.populateDataForDataDump()).thenCallRealMethod();
        String response = matchingAlgoController.populateDataForDataDump();
        assertNull(response);
    }

    @Test
    public void itemCountForSerials() throws Exception {
        List<String> allInstitutionCodeExceptSupportInstitution=Arrays.asList(ScsbCommonConstants.COLUMBIA,ScsbCommonConstants.PRINCETON,ScsbCommonConstants.NYPL);
        Mockito.when(institutionDetailsRepository.findAllInstitutionCodesExceptSupportInstitution(Mockito.anyString())).thenReturn(allInstitutionCodeExceptSupportInstitution);
        Mockito.when(commonUtil.findAllInstitutionCodesExceptSupportInstitution()).thenReturn(allInstitutionCodeExceptSupportInstitution);

        Mockito.when(matchingAlgoController.getMatchingAlgorithmUpdateCGDService()).thenReturn(matchingAlgorithmUpdateCGDService);
        Mockito.doNothing().when(matchingAlgorithmUpdateCGDService).getItemsCountForSerialsMatching(batchSize);
        Mockito.when(matchingAlgoController.itemCountForSerials()).thenCallRealMethod();
        String response = matchingAlgoController.itemCountForSerials();
        assertTrue(response.contains("Items Count"));
    }

    @Test
    public void testItemCountForSerials() throws Exception {
        ReportDataEntity reportDataEntity = new ReportDataEntity();
        reportDataEntity.setHeaderValue("1234");
        int totalPagesCount = 1;
        List<String> allInstitutionCodeExceptSupportInstitution=Arrays.asList(ScsbCommonConstants.COLUMBIA,ScsbCommonConstants.PRINCETON,ScsbCommonConstants.NYPL);
        Mockito.when(institutionDetailsRepository.findAllInstitutionCodesExceptSupportInstitution(Mockito.anyString())).thenReturn(allInstitutionCodeExceptSupportInstitution);
        Mockito.when(commonUtil.findAllInstitutionCodesExceptSupportInstitution()).thenReturn(allInstitutionCodeExceptSupportInstitution);

        Mockito.when(matchingAlgoController.getMatchingAlgorithmUpdateCGDService()).thenReturn(matchingAlgorithmUpdateCGDService);
        Mockito.doCallRealMethod().when(matchingAlgorithmUpdateCGDService).getItemsCountForSerialsMatching(batchSize);
        Mockito.when(matchingAlgorithmUpdateCGDService.getReportDataDetailsRepository()).thenReturn(reportDataDetailsRepository);
        Mockito.when(matchingAlgorithmUpdateCGDService.getBibliographicDetailsRepository()).thenReturn(mockedBibliographicDetailsRepository);
        Mockito.when(matchingAlgorithmUpdateCGDService.getCollectionGroupMap()).thenReturn(collectionGroupMap);
        Mockito.when(matchingAlgorithmUpdateCGDService.getCollectionGroupMap().get(ScsbCommonConstants.SHARED_CGD)).thenReturn(1);
        Mockito.when(matchingAlgorithmUpdateCGDService.getBibliographicDetailsRepository().findByIdIn(Mockito.any())).thenReturn(Arrays.asList(saveBibSingleHoldingsSingleItem()));
        Mockito.when(matchingAlgorithmUpdateCGDService.getReportDataDetailsRepository().getCountOfRecordNumForMatchingSerials(ScsbCommonConstants.BIB_ID)).thenReturn(Long.valueOf(10000));
        for(int pageNum = 0; pageNum < totalPagesCount + 1; pageNum++) {
            long from = pageNum * Long.valueOf(batchSize);
            Mockito.when(matchingAlgorithmUpdateCGDService.getReportDataDetailsRepository().getReportDataEntityForMatchingSerials(ScsbCommonConstants.BIB_ID, from, batchSize)).thenReturn(Arrays.asList(reportDataEntity));
        }
        Mockito.when(matchingAlgoController.itemCountForSerials()).thenCallRealMethod();
        String response = matchingAlgoController.itemCountForSerials();
        assertTrue(response.contains("Items Count"));
    }

    @Test
    public void checkGetterServices() throws Exception {
        Mockito.when(matchingAlgoController.getMatchingBibInfoDetailService()).thenCallRealMethod();
        Mockito.when(matchingAlgoController.getMatchingAlgorithmHelperService()).thenCallRealMethod();
        Mockito.when(matchingAlgoController.getMatchingAlgoBatchSize()).thenCallRealMethod();
        Mockito.when(matchingAlgoController.getReportGenerator()).thenCallRealMethod();
        Mockito.when(matchingAlgoController.getMatchingBibItemIndexExecutorService()).thenCallRealMethod();
        Mockito.when(matchingAlgoController.getMatchingAlgorithmUpdateCGDService()).thenCallRealMethod();
        Mockito.when(matchingAlgoController.getMatchingAlgoBatchSize()).thenCallRealMethod();
        assertNotEquals(matchingAlgorithmUpdateCGDService, matchingAlgoController.getMatchingAlgorithmUpdateCGDService());
        assertNotEquals(matchingAlgorithmHelperService, matchingAlgoController.getMatchingAlgorithmHelperService());
        assertNotEquals(matchingBibInfoDetailService, matchingAlgoController.getMatchingBibInfoDetailService());
        assertNotEquals(String.valueOf(batchSize), matchingAlgoController.getMatchingAlgoBatchSize());
        assertNotEquals(matchingBibItemIndexExecutorService, matchingAlgoController.getMatchingBibItemIndexExecutorService());
        assertNotEquals(reportGenerator, matchingAlgoController.getReportGenerator());
    }

    public BibliographicEntity saveBibSingleHoldingsSingleItem() throws Exception {

        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("UC");
        institutionEntity.setInstitutionName("University of Chicago");
        assertNotNull(institutionEntity);

        Random random = new Random();
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setId(1134);
        bibliographicEntity.setContent("mock Content".getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setCreatedBy("tst");
        bibliographicEntity.setLastUpdatedBy("tst");
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setOwningInstitutionBibId(String.valueOf(random.nextInt()));
        bibliographicEntity.setInstitutionEntity(institutionEntity);
        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setCreatedBy("tst");
        holdingsEntity.setLastUpdatedBy("tst");
        holdingsEntity.setOwningInstitutionId(1);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setOwningInstitutionItemId(String.valueOf(random.nextInt()));
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setBarcode("512356");
        itemEntity.setCallNumber("x.12321");
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCallNumberType("1");
        itemEntity.setCustomerCode("123");
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("tst");
        itemEntity.setLastUpdatedBy("tst");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        itemEntity.setInstitutionEntity(institutionEntity);
        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));
        return bibliographicEntity;
    }

    public Page<Integer> getRecordNumber(){
        Page<Integer> recordNumber = new Page<Integer>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Integer, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Integer> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Integer> iterator() {
                return null;
            }
        };
        return recordNumber;

    }

}
