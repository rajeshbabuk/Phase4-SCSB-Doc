package org.recap.executors;

import org.apache.camel.ProducerTemplate;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.recap.BaseTestCaseUT;
import org.recap.BaseTestCaseUT4;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.model.jpa.MatchingMatchPointsEntity;
import org.recap.model.search.resolver.BibValueResolver;
import org.recap.repository.jpa.MatchingMatchPointsDetailsRepository;
import org.recap.util.MatchingAlgorithmUtil;
import org.recap.util.SolrQueryBuilder;
import org.springframework.data.solr.core.SolrTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(PowerMockRunner.class)
@PrepareForTest({SolrTemplate.class, SolrClient.class})
public class SaveMatchingBibsCallableUT extends BaseTestCaseUT4 {

    @Mock
    MatchingMatchPointsDetailsRepository matchingMatchPointsDetailsRepository;

    @Mock
    ProducerTemplate producer;

    @Mock
    SolrQueryBuilder solrQueryBuilder;

    @Mock
    MatchingAlgorithmUtil matchingAlgorithmUtil;

    @InjectMocks
    SaveMatchingBibsCallable mockSaveMatchingBibsCallable;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveMatchingBibsCallable() throws Exception {
        SolrTemplate solrTemplate = PowerMockito.mock(SolrTemplate.class);
        String matchCriteria="test";
        long batchSize=2l;
        int pageNum=1;
        List<MatchingMatchPointsEntity> matchPointsEntityList=new ArrayList<>();
        MatchingMatchPointsEntity matchingMatchPointsEntity=new MatchingMatchPointsEntity();
        matchPointsEntityList.add(matchingMatchPointsEntity);
        SaveMatchingBibsCallable saveMatchingBibsCallable=new SaveMatchingBibsCallable(matchingMatchPointsDetailsRepository,matchCriteria,solrTemplate,producer,solrQueryBuilder,batchSize,pageNum,matchingAlgorithmUtil);
        Mockito.when(matchingMatchPointsDetailsRepository.getMatchPointEntityByCriteria(matchCriteria,pageNum*batchSize,batchSize)).thenReturn(matchPointsEntityList);
        Mockito.when(solrQueryBuilder.solrQueryToFetchBibDetails(Mockito.anyList(),Mockito.anyList(),Mockito.anyString())).thenReturn(new SolrQuery());
        SolrClient solrClient=PowerMockito.mock(SolrClient.class);
        PowerMockito.when(solrTemplate.getSolrClient()).thenReturn(solrClient);
        QueryResponse queryResponse=Mockito.mock(QueryResponse.class);
        Mockito.when(solrClient.query(Mockito.any())).thenReturn(queryResponse);
        SolrDocumentList solrDocumentList = getSolrDocumentList();
        Mockito.when(queryResponse.getResults()).thenReturn(solrDocumentList);
        Set<Integer> bibIdList=new HashSet<>();
        bibIdList.add(1);
        saveMatchingBibsCallable.setBibIdList(bibIdList);
        List<BibValueResolver> bibValueResolvers=saveMatchingBibsCallable.getBibValueResolvers();
        assertNotNull(bibValueResolvers);
        Object object = saveMatchingBibsCallable.call();
        assertEquals(1,object);
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
}
