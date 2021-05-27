package org.recap.controller;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.BaseTestCaseUT;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.admin.SolrAdmin;
import org.recap.executors.BibItemIndexExecutorService;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.solr.SolrIndexRequest;
import org.recap.repository.jpa.BibliographicDetailsRepository;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.recap.repository.solr.main.BibSolrCrudRepository;
import org.recap.repository.solr.main.HoldingsSolrCrudRepository;
import org.recap.repository.solr.main.ItemCrudRepository;
import org.recap.service.accession.SolrIndexService;
import org.recap.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by premkb on 2/8/16.
 */
public class SolrIndexControllerUT extends BaseTestCaseUT {
    private static final Logger logger = LoggerFactory.getLogger(SolrIndexControllerUT.class);

    @InjectMocks
    SolrIndexController solrIndexController;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @Mock
    SolrIndexService solrIndexService;

    @Mock
    BibItemIndexExecutorService bibItemIndexExecutorService;

    @Mock
    BibSolrCrudRepository bibSolrCrudRepository;

    @Mock
    HoldingsSolrCrudRepository holdingsSolrCrudRepository;

    @Mock
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @Mock
    BibliographicEntity bibliographicEntity;

    @Mock
    ItemCrudRepository itemCrudRepository;

    @Mock
    SolrAdmin solrAdmin;

    @Mock
    InstitutionDetailsRepository institutionDetailsRepository;

    @Mock
    CommonUtil commonUtil;

    @BeforeEach
    public void setUp()throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void solrIndexer()throws Exception{
        String response =solrIndexController.solrIndexer(model);
        assertEquals("solrIndexer",response);
    }

    @Test
    public void fullIndex()throws Exception{
        String response =solrIndexController.fullIndex(getSolrIndexRequest(),bindingResult,model);
        assertNotNull(response);
        assertTrue(response.contains("Total number of records processed :"));
    }

    @Test
    public void fullIndexException()throws Exception{
        SolrIndexRequest solrIndexRequest = getSolrIndexRequest();
        solrIndexRequest.setOwningInstitutionCode("PUL");
        Mockito.doThrow(SolrServerException.class).when(solrAdmin).unloadTempCores();
        String response =solrIndexController.fullIndex(solrIndexRequest,bindingResult,model);
        assertNotNull(response);
        assertTrue(response.contains("Total number of records processed :"));
    }


    @Test
    public void fullIndex_Noinst()throws Exception{
        String response =solrIndexController.fullIndex(getSolrIndexRequest(),bindingResult,model);
        assertNotNull(response);
        assertTrue(response.contains("Total number of records processed :"));
    }

    @Test
    public void report()throws Exception{
        String response =solrIndexController.report("");
        assertNotNull(response);
        assertTrue(response.contains("Index process initiated!"));
    }

    @Test
    public void indexByOwningInstBibliographicIdList()throws Exception{
        String response =solrIndexController.indexByOwningInstBibliographicIdList(getRequestParameters());
        assertNotNull(response);
        assertTrue(response.contains(ScsbCommonConstants.SUCCESS));
    }

    @Test
    public void indexByOwningInstBibliographicIdListException()throws Exception{
        Mockito.doThrow(NullPointerException.class).when(solrIndexService).indexByOwnInstBibId(Mockito.anyList(),Mockito.anyInt());
        String response =solrIndexController.indexByOwningInstBibliographicIdList(getRequestParameters());
        assertNotNull(response);
        assertTrue(response.contains(ScsbCommonConstants.FAILURE));
    }

    private Map<String, Object> getRequestParameters() {
        Map<String,Object> requestParameters = new HashMap<>();
        requestParameters.put(ScsbConstants.OWN_INST_BIBID_LIST, "[123/456/789]");
        requestParameters.put(ScsbCommonConstants.OWN_INSTITUTION_ID,"1");
        return requestParameters;
    }

    @Test
    public void deleteByBibIdAndIsDeletedFlag()throws Exception{
        String response =solrIndexController.deleteByBibIdAndIsDeletedFlag(getBibIdMapToRemoveIndexList());
        assertNotNull(response);
        assertTrue(response.contains(ScsbCommonConstants.SUCCESS));
    }

    @Test
    public void deleteByBibIdAndIsDeletedFlagException()throws Exception{
        Mockito.doThrow(NullPointerException.class).when(solrIndexService).deleteBySolrQuery(Mockito.anyString());
        String response =solrIndexController.deleteByBibIdAndIsDeletedFlag(getBibIdMapToRemoveIndexList());
        assertNotNull(response);
        assertTrue(response.contains(ScsbCommonConstants.FAILURE));
    }

    private List<Map<String, String>> getBibIdMapToRemoveIndexList() {
        List<Map<String, String>> bibIdMapToRemoveIndexList = new ArrayList<>();
        bibIdMapToRemoveIndexList.add(getStringStringMap("1"));
        return bibIdMapToRemoveIndexList;
    }

    @Test
    public void deleteByBibHoldingItemId()throws Exception{
        String response =solrIndexController.deleteByBibHoldingItemId(getidMapToRemoveIndexList());
        assertNotNull(response);
        assertTrue(response.contains(ScsbCommonConstants.SUCCESS));
    }

    @Test
    public void deleteByBibHoldingItemIdException()throws Exception{
        Mockito.doThrow(NullPointerException.class).when(solrIndexService).deleteBySolrQuery(Mockito.anyString());
        String response =solrIndexController.deleteByBibHoldingItemId(getidMapToRemoveIndexList());
        assertNotNull(response);
    }

    private List<Map<String, String>> getidMapToRemoveIndexList() {
        List<Map<String, String>> idMapToRemoveIndexList = getBibIdMapToRemoveIndexList();
        idMapToRemoveIndexList.add(getStringStringMap(""));
        idMapToRemoveIndexList.add(getStringStringMap(null));
        return idMapToRemoveIndexList;
    }

    private Map<String, String> getStringStringMap(String root) {
        Map<String,String> idMapToRemoveIndex=new HashMap<>();
        idMapToRemoveIndex.put("BibId","1");
        idMapToRemoveIndex.put(ScsbCommonConstants.IS_DELETED_BIB,"1");
        idMapToRemoveIndex.put("HoldingId","1");
        idMapToRemoveIndex.put("ItemId","1");
        idMapToRemoveIndex.put("_root_",root);
        return idMapToRemoveIndex;
    }

    @Test
    public void partialIndex()throws Exception{
        SolrIndexRequest solrIndexRequest = getSolrIndexRequest();
        solrIndexRequest.setOwningInstitutionCode("PUL");
        solrIndexRequest.setCommitInterval(5000);
        String response =solrIndexController.partialIndex(solrIndexRequest,bindingResult,model);
        assertNotNull(response);
        assertTrue(response.contains("Total number of records processed :"));
    }

    @Test
    public void partialIndex_else()throws Exception{
        String response =solrIndexController.partialIndex(getSolrIndexRequest(),bindingResult,model);
        assertNotNull(response);
        assertTrue(response.contains("Total number of records processed :"));
    }

    @Test
    public void indexByBibliographicId()throws Exception{
        String response =solrIndexController.indexByBibliographicId(getBibliographicIdList());
        assertNotNull(response);
        assertTrue(response.contains(ScsbCommonConstants.SUCCESS));
    }

    @Test
    public void getInstitution(){
        List<String> institutionCodes= Arrays.asList(ScsbCommonConstants.NYPL,ScsbCommonConstants.COLUMBIA,ScsbCommonConstants.PRINCETON);
        Mockito.when(commonUtil.findAllInstitutionCodesExceptSupportInstitution()).thenReturn(institutionCodes);
        List<String> institution=solrIndexController.getInstitution();
        assertNotNull(institution);
        assertTrue(institution.get(0).contains(ScsbCommonConstants.NYPL));
    }


    @Test
    public void indexByBibliographicIdException()throws Exception{
        Mockito.when(solrIndexService.indexByBibliographicId(Mockito.anyInt())).thenThrow(NullPointerException.class);
        String response =solrIndexController.indexByBibliographicId(getBibliographicIdList());
        assertNotNull(response);
        assertTrue(response.contains(ScsbCommonConstants.FAILURE));
    }

    private List<Integer> getBibliographicIdList() {
        List<Integer> bibliographicIdList = new ArrayList<>();
        bibliographicIdList.add(947);
        bibliographicIdList.add(1215);
        return bibliographicIdList;
    }

    private SolrIndexRequest getSolrIndexRequest(){
        SolrIndexRequest solrIndexRequest = new SolrIndexRequest();
        solrIndexRequest.setNumberOfThreads(5);
        solrIndexRequest.setNumberOfDocs(1000);
        solrIndexRequest.setDocType("");
        solrIndexRequest.setDoClean(true);
        return solrIndexRequest;
    }

}