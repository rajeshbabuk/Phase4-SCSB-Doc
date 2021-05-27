package org.recap.controller;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.recap.BaseTestCaseUT;
import org.recap.BaseTestCaseUT4;
import org.recap.ScsbCommonConstants;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SolrTemplate.class, SolrClient.class})
public class AccessionReconcilationRestControllerUT extends BaseTestCaseUT4 {

    @InjectMocks
    AccessionReconcilationRestController accessionReconcilationRestController;

    @Before
    public void setUp()throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void startAccessionReconcilation()throws Exception{
        Map<String,String> barcodesAndCustomerCodes=new HashMap<>();
        barcodesAndCustomerCodes.put("barcode","123456");
        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        SolrClient solrClient=PowerMockito.mock(SolrClient.class);
        PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
        QueryResponse queryResponse= Mockito.mock(QueryResponse.class);
        Mockito.when(solrClient.query(Mockito.any(SolrQuery.class),Mockito.any())).thenReturn(queryResponse);
        Map<String, FieldStatsInfo> getFieldStatsInfo=new HashMap<>();
        FieldStatsInfo fieldStatsInfo=Mockito.mock(FieldStatsInfo.class);
        Collection<Object> barcodes=new ArrayList<>();
        barcodes.add("123456");
        Mockito.when(fieldStatsInfo.getDistinctValues()).thenReturn(barcodes);
        getFieldStatsInfo.put(ScsbCommonConstants.BARCODE,fieldStatsInfo);
        Mockito.when(queryResponse.getFieldStatsInfo()).thenReturn(getFieldStatsInfo);
        ReflectionTestUtils.setField(accessionReconcilationRestController,"solrTemplate",mocksolrTemplate1);
        Map<String,String> responseMessage=accessionReconcilationRestController.startAccessionReconcilation(barcodesAndCustomerCodes);
        assertNotNull(responseMessage);
        assertEquals(barcodesAndCustomerCodes,responseMessage);
    }

}
