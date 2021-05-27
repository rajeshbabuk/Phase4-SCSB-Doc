package org.recap.repository.solr.impl;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.recap.BaseTestCaseUT;
import org.recap.BaseTestCaseUT4;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.model.search.SearchRecordsRequest;
import org.recap.model.search.resolver.HoldingsValueResolver;
import org.recap.model.solr.BibItem;
import org.recap.model.solr.Item;
import org.recap.util.CommonUtil;
import org.recap.util.SolrQueryBuilder;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 22/2/17.
 */


@RunWith(PowerMockRunner.class)
@PrepareForTest({SolrTemplate.class, SolrClient.class})
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
public class BibSolrDocumentRepositoryImplUT extends BaseTestCaseUT4 {

    @InjectMocks
    BibSolrDocumentRepositoryImpl bibSolrDocumentRepository;

    @Mock
    private CommonUtil commonUtil;

    @Mock
    private SolrQueryBuilder solrQueryBuilder;

    @Mock
    HoldingsValueResolver holdingsValueResolver;

    @Test
    public void searchIsEmptyField() throws Exception{
        SearchRecordsRequest searchRecordsRequest=new SearchRecordsRequest();
        searchRecordsRequest.setRequestingInstitution("NYPL");
        searchRecordsRequest.setFieldValue("Shared");
        searchRecordsRequest.setFieldName("test");
        searchRecordsRequest.setPageSize(1);
        searchRecordsRequest.setPageNumber(1);
        searchRecordsRequest.setCatalogingStatus("Shared");
        SolrQuery queryForParentAndChildCriteria=new SolrQuery();
        Mockito.when(solrQueryBuilder.getQueryForParentAndChildCriteria(Mockito.any())).thenReturn(queryForParentAndChildCriteria);
        Mockito.when(solrQueryBuilder.getCountQueryForChildAndParentCriteria(Mockito.any())).thenReturn(queryForParentAndChildCriteria);
        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        SolrClient solrClient=PowerMockito.mock(SolrClient.class);
        ReflectionTestUtils.setField(bibSolrDocumentRepository,"solrTemplate",mocksolrTemplate1);
        PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
        QueryResponse queryResponse= Mockito.mock(QueryResponse.class);
        Mockito.when(solrClient.query(Mockito.any(SolrQuery.class))).thenReturn(queryResponse);
        SolrDocumentList solrDocumentList = getSolrDocumentList();
        Mockito.when(queryResponse.getResults()).thenReturn(solrDocumentList);
        Mockito.when(commonUtil.getSolrDocumentsByDocType(null,mocksolrTemplate1)).thenReturn(solrDocumentList);
        BibItem bibItem=new BibItem();
        Mockito.when(commonUtil.getBibItemFromSolrFieldNames(Mockito.any(),Mockito.anyCollection(),Mockito.any())).thenReturn(bibItem);
        Item item=new Item();
        item.setDocType("doctype");
        Mockito.when(commonUtil.getItem(Mockito.any())).thenReturn(item);

        Map<String, Object> search=bibSolrDocumentRepository.search(searchRecordsRequest);
        assertTrue(search.containsKey(ScsbCommonConstants.SEARCH_SUCCESS_RESPONSE));
    }

    @Test
    public void search() throws Exception{
        SearchRecordsRequest searchRecordsRequest=new SearchRecordsRequest();
        SolrQuery queryForParentAndChildCriteria=new SolrQuery();
        Mockito.when(solrQueryBuilder.getQueryForParentAndChildCriteria(Mockito.any())).thenReturn(queryForParentAndChildCriteria);
        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        SolrClient solrClient=PowerMockito.mock(SolrClient.class);
        ReflectionTestUtils.setField(bibSolrDocumentRepository,"solrTemplate",mocksolrTemplate1);
        PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
        QueryResponse queryResponse= Mockito.mock(QueryResponse.class);
        Mockito.when(solrClient.query(Mockito.any(SolrQuery.class))).thenReturn(queryResponse);
        Map<String, Object> search=bibSolrDocumentRepository.search(searchRecordsRequest);
        assertTrue(search.containsKey(ScsbCommonConstants.SEARCH_SUCCESS_RESPONSE));
    }

    @Test
    public void searchException() throws Exception{
        SearchRecordsRequest searchRecordsRequest=new SearchRecordsRequest();
        SolrQuery queryForParentAndChildCriteria=new SolrQuery();
        Mockito.when(solrQueryBuilder.getQueryForParentAndChildCriteria(Mockito.any())).thenReturn(queryForParentAndChildCriteria);
        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        SolrClient solrClient=PowerMockito.mock(SolrClient.class);
        ReflectionTestUtils.setField(bibSolrDocumentRepository,"solrTemplate",mocksolrTemplate1);
        PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
        Mockito.when(solrClient.query(Mockito.any(SolrQuery.class))).thenThrow(SolrServerException.class);
        Map<String, Object> search=bibSolrDocumentRepository.search(searchRecordsRequest);
        assertTrue(search.containsKey(ScsbCommonConstants.SEARCH_ERROR_RESPONSE));
    }


    @Test
    public void searchIncompleteItem() throws Exception{
        SearchRecordsRequest searchRecordsRequest=new SearchRecordsRequest();
        searchRecordsRequest.setFieldValue("Shared");
        searchRecordsRequest.setFieldName("");
        searchRecordsRequest.setSortIncompleteRecords(true);
        SolrQuery queryForParentAndChildCriteria=new SolrQuery();
        Mockito.when(solrQueryBuilder.getQueryForParentAndChildCriteria(Mockito.any())).thenReturn(queryForParentAndChildCriteria);
        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        SolrClient solrClient=PowerMockito.mock(SolrClient.class);
        ReflectionTestUtils.setField(bibSolrDocumentRepository,"solrTemplate",mocksolrTemplate1);
        PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
        QueryResponse queryResponse= Mockito.mock(QueryResponse.class);
        Mockito.when(solrClient.query(Mockito.any(SolrQuery.class))).thenReturn(queryResponse);
        Mockito.when(solrQueryBuilder.getQueryForChildAndParentCriteria(Mockito.any())).thenReturn(queryForParentAndChildCriteria);
        Map<String, Object> search=bibSolrDocumentRepository.search(searchRecordsRequest);
        assertTrue(search.containsKey(ScsbCommonConstants.SEARCH_SUCCESS_RESPONSE));
    }

    @Test
    public void searchIsItemField() throws Exception{
        SearchRecordsRequest searchRecordsRequest=new SearchRecordsRequest();
        searchRecordsRequest.setRequestingInstitution("NYPL");
        searchRecordsRequest.setFieldValue("NA");
        searchRecordsRequest.setFieldName(ScsbCommonConstants.CUSTOMER_CODE);
        searchRecordsRequest.setPageSize(1);
        searchRecordsRequest.setPageNumber(1);
        searchRecordsRequest.setCatalogingStatus("Shared");
        SolrQuery queryForParentAndChildCriteria=new SolrQuery();
        Mockito.when(solrQueryBuilder.getQueryForChildAndParentCriteria(Mockito.any())).thenReturn(queryForParentAndChildCriteria);
        Mockito.when(solrQueryBuilder.getCountQueryForParentAndChildCriteria(Mockito.any())).thenReturn(queryForParentAndChildCriteria);
        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        SolrClient solrClient=PowerMockito.mock(SolrClient.class);
        ReflectionTestUtils.setField(bibSolrDocumentRepository,"solrTemplate",mocksolrTemplate1);
        PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
        QueryResponse queryResponse= Mockito.mock(QueryResponse.class);
        Mockito.when(solrClient.query(Mockito.any(SolrQuery.class))).thenReturn(queryResponse);
        SolrDocumentList solrDocumentList = getSolrDocumentList();
        Mockito.when(queryResponse.getResults()).thenReturn(solrDocumentList);
        Mockito.when(commonUtil.getSolrDocumentsByDocType(null,mocksolrTemplate1)).thenReturn(solrDocumentList);
        BibItem bibItem=new BibItem();
        Mockito.when(commonUtil.getBibItemFromSolrFieldNames(Mockito.any(),Mockito.anyCollection(),Mockito.any())).thenReturn(bibItem);
        Item item=new Item();
        item.setDocType("doctype");
        Mockito.when(commonUtil.getItem(Mockito.any())).thenReturn(item);
        Map<String, Object> search=bibSolrDocumentRepository.search(searchRecordsRequest);
        assertTrue(search.containsKey(ScsbCommonConstants.SEARCH_SUCCESS_RESPONSE));
    }

    @Test
    public void getPageNumberOnPageSizeChangeException() throws Exception{
        SearchRecordsRequest searchRecordsRequest = new SearchRecordsRequest();
        searchRecordsRequest.setTotalBibRecordsCount("U");
        searchRecordsRequest.setPageSize(1);
        searchRecordsRequest.setPageNumber(1);
        int page = bibSolrDocumentRepository.getPageNumberOnPageSizeChange(searchRecordsRequest);
        assertEquals(1, page);
    }

    @Test
    public void getPageNumberOnPageSizeChange() throws Exception{
        String[] fields={ScsbCommonConstants.CUSTOMER_CODE,"","test"};
        for (String field:fields) {
            SearchRecordsRequest searchRecordsRequest = new SearchRecordsRequest();
            searchRecordsRequest.setRequestingInstitution("NYPL");
            searchRecordsRequest.setFieldValue("NA");
            searchRecordsRequest.setFieldName(field);
            searchRecordsRequest.setPageSize(1);
            searchRecordsRequest.setPageNumber(1);
            searchRecordsRequest.setTotalBibRecordsCount("1");
            searchRecordsRequest.setCatalogingStatus("Shared");
            SolrQuery queryForParentAndChildCriteria = new SolrQuery();
            Mockito.when(solrQueryBuilder.getQueryForChildAndParentCriteria(Mockito.any())).thenReturn(queryForParentAndChildCriteria);
            Mockito.when(solrQueryBuilder.getCountQueryForParentAndChildCriteria(Mockito.any())).thenReturn(queryForParentAndChildCriteria);
            SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
            SolrClient solrClient = PowerMockito.mock(SolrClient.class);
            ReflectionTestUtils.setField(bibSolrDocumentRepository, "solrTemplate", mocksolrTemplate1);
            PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
            QueryResponse queryResponse = Mockito.mock(QueryResponse.class);
            Mockito.when(solrClient.query(Mockito.any(SolrQuery.class))).thenReturn(queryResponse);
            SolrDocumentList solrDocumentList = getSolrDocumentList();
            Mockito.when(queryResponse.getResults()).thenReturn(solrDocumentList);
            Mockito.when(commonUtil.getSolrDocumentsByDocType(null, mocksolrTemplate1)).thenReturn(solrDocumentList);
            BibItem bibItem = new BibItem();
            Mockito.when(commonUtil.getBibItemFromSolrFieldNames(Mockito.any(), Mockito.anyCollection(), Mockito.any())).thenReturn(bibItem);
            Item item = new Item();
            item.setDocType("doctype");
            Mockito.when(commonUtil.getItem(Mockito.any())).thenReturn(item);
            int page = bibSolrDocumentRepository.getPageNumberOnPageSizeChange(searchRecordsRequest);
            assertNotNull(page);
        }
    }

    private SolrDocumentList getSolrDocumentList() {
        SolrDocumentList solrDocumentList = new SolrDocumentList();
        SolrDocument solrDocument = new SolrDocument();
        solrDocument.setField(ScsbCommonConstants.DOCTYPE,ScsbCommonConstants.ITEM);
        solrDocument.setField(ScsbCommonConstants.IS_DELETED_ITEM,false);
        solrDocument.setField(ScsbConstants.ITEM_CATALOGING_STATUS,"Shared");
        solrDocument.setField("test","all");
        SolrDocument solrDocument1 = new SolrDocument();
        solrDocument1.setField(ScsbCommonConstants.DOCTYPE,ScsbCommonConstants.HOLDINGS);
        solrDocument1.setField(ScsbCommonConstants.IS_DELETED_HOLDINGS,false);
        SolrDocument solrDocument2 = new SolrDocument();
        solrDocument2.setField(ScsbCommonConstants.DOCTYPE,ScsbCommonConstants.BIB);
        solrDocument2.setField(ScsbCommonConstants.IS_DELETED_BIB,false);
        solrDocument2.setField(ScsbConstants.BIB_CATALOGING_STATUS,"Shared");
        solrDocumentList.setNumFound(1l);
        solrDocumentList.add(0,solrDocument);
        solrDocumentList.add(1,solrDocument1);
        solrDocumentList.add(2,solrDocument2);
        return  solrDocumentList;
    }


    public Map bibValueResolvers(){
        Map<String,Object> bibResolverMap = new HashMap<>();
        bibResolverMap.put("_root_","root");
        bibResolverMap.put("Author_display","John");
        bibResolverMap.put("Author_search",Arrays.asList("Test"));
        bibResolverMap.put("BibId",1);
        bibResolverMap.put("DocType","Barcode");
        bibResolverMap.put("id","1");
        bibResolverMap.put("Imprint","test");
        bibResolverMap.put("ISBN",Arrays.asList("001"));
        bibResolverMap.put("ISSN",Arrays.asList("123"));
        bibResolverMap.put("LCCN","003");
        bibResolverMap.put("LeaderMaterialType","Others");
        bibResolverMap.put("MaterialType","Others");
        bibResolverMap.put("notes","test");
        bibResolverMap.put("OCLCNumber",Arrays.asList("123"));
        bibResolverMap.put("OwningInstitutionBibId","12345");
        bibResolverMap.put("BibOwningInstitution","PUL");
        bibResolverMap.put("PublicationDate",new Date().toString());
        bibResolverMap.put("PublicationPlace","test");
        bibResolverMap.put("Publisher","test");
        bibResolverMap.put("Subject","test");
        bibResolverMap.put("Title_display","test");
        bibResolverMap.put("Title_search","test");
        bibResolverMap.put("Title_sort","test");
        bibResolverMap.put(ScsbCommonConstants.IS_DELETED_BIB,false);
        return bibResolverMap;
    }

    public Map itemValueResolvers(){
        Map<String,Object> itemResolverMap = new HashMap<>();
        itemResolverMap.put("Availability_search","Test");
        itemResolverMap.put("Availability_display","Test");
        itemResolverMap.put("Barcode","456321");
        itemResolverMap.put("CallNumber_search","search");
        itemResolverMap.put("CallNumber_display","Test");
        itemResolverMap.put("CollectionGroupDesignation","Open");
        itemResolverMap.put("CustomerCode","PB");
        itemResolverMap.put("DocType","itemID");
        itemResolverMap.put("ItemOwningInstitution","PUL");
        itemResolverMap.put("UseRestriction_search","No Restriction");
        itemResolverMap.put("UseRestriction_display","No Restriction");
        itemResolverMap.put("VolumePartYear","2016");
        itemResolverMap.put("_root_","root");
        itemResolverMap.put("ItemId",1);
        itemResolverMap.put("id","1236598");
        itemResolverMap.put(ScsbCommonConstants.IS_DELETED_ITEM,false);
        return itemResolverMap;
    }

    public Map holdingValueResolvers(){
        Map<String,Object> holdingResolverMap = new HashMap<>();
        holdingResolverMap.put("_root_","root");
        holdingResolverMap.put("SummaryHoldings","Test");
        holdingResolverMap.put("DocType","holdingId");
        holdingResolverMap.put("id","565456456");
        holdingResolverMap.put("HoldingsId",534541);
        holdingResolverMap.put(ScsbCommonConstants.IS_DELETED_HOLDINGS,false);
        return holdingResolverMap;
    }

}