package org.recap.camel.processor;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.model.jpa.ItemEntity;
import org.recap.model.jpa.MatchingBibEntity;
import org.recap.model.jpa.MatchingMatchPointsEntity;
import org.recap.model.jpa.ReportEntity;
import org.recap.repository.jpa.ItemDetailsRepository;
import org.recap.repository.jpa.MatchingBibDetailsRepository;
import org.recap.repository.jpa.MatchingMatchPointsDetailsRepository;
import org.recap.repository.jpa.ReportDetailRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by angelind on 9/1/17.
 */
public class MatchingAlgorithmProcessorUT extends BaseTestCaseUT {

    @InjectMocks
    MatchingAlgorithmProcessor matchingAlgorithmProcessor;

    @Mock
    MatchingBibDetailsRepository matchingBibDetailsRepository;

    @Mock
    MatchingMatchPointsDetailsRepository matchingMatchPointsDetailsRepository;

    @Mock
    ItemDetailsRepository itemDetailsRepository;

    @Mock
    ReportDetailRepository reportDetailRepository;

    @Test
    public void updateItemEntityTest() throws Exception {
        Mockito.when(itemDetailsRepository.saveAll(Mockito.anyCollection())).thenReturn(new ArrayList<>());
        matchingAlgorithmProcessor.updateItemEntity(Arrays.asList(new ItemEntity()));
        assertNotNull(matchingAlgorithmProcessor);
    }

    @Test
    public void saveMatchingReportEntity() throws Exception {
        Mockito.when(reportDetailRepository.saveAll(Mockito.anyCollection())).thenReturn(new ArrayList<>());
        matchingAlgorithmProcessor.saveMatchingReportEntity(Arrays.asList(new ReportEntity()));
        assertNotNull(matchingAlgorithmProcessor);
    }

    @Test
    public void updateMatchingBibEntityTest() throws Exception {
        Mockito.when(matchingBibDetailsRepository.updateStatusBasedOnBibs(Mockito.anyString(),Mockito.anyList())).thenReturn(1);
        matchingAlgorithmProcessor.updateMatchingBibEntity(getMap());
        assertNotNull(matchingAlgorithmProcessor);
    }

    private Map getMap() {
        Map matchingBibMap=new HashMap();
        matchingBibMap.put(ScsbCommonConstants.STATUS,ScsbCommonConstants.COMPLETE_STATUS);
        matchingBibMap.put(ScsbConstants.MATCHING_BIB_IDS,Arrays.asList(1));
        return matchingBibMap;
    }

    @Test
    public void updateMatchingBibEntityTestException() throws Exception {
        Mockito.when(matchingBibDetailsRepository.updateStatusBasedOnBibs(Mockito.anyString(),Mockito.anyList())).thenThrow(NullPointerException.class);
        matchingAlgorithmProcessor.updateMatchingBibEntity( getMap());
        assertNotNull(matchingAlgorithmProcessor);
    }

    @Test
    public void saveMatchingBibEntity() throws Exception {
        Mockito.when(matchingBibDetailsRepository.saveAll(Mockito.anyCollection())).thenReturn(new ArrayList<>());
        matchingAlgorithmProcessor.saveMatchingBibEntity(Arrays.asList(new MatchingBibEntity()));
        assertNotNull(matchingAlgorithmProcessor);
    }

    @Test
    public void saveMatchingBibEntityException1() throws Exception {
        Mockito.when(matchingBibDetailsRepository.saveAll(Mockito.anyCollection())).thenThrow(NullPointerException.class);
        matchingAlgorithmProcessor.saveMatchingBibEntity(getMatchingBibEntities());
        assertNotNull(matchingAlgorithmProcessor);
    }

    @Test
    public void saveMatchingBibEntityException() throws Exception {
        Mockito.when(matchingBibDetailsRepository.save(Mockito.any())).thenThrow(NullPointerException.class);
        Mockito.when(matchingBibDetailsRepository.saveAll(Mockito.anyCollection())).thenThrow(NullPointerException.class);
        matchingAlgorithmProcessor.saveMatchingBibEntity(getMatchingBibEntities());
        assertNotNull(matchingAlgorithmProcessor);
    }

    private List<MatchingBibEntity> getMatchingBibEntities() {
        List<MatchingBibEntity> matchingBibEntities=new ArrayList<>();
        MatchingBibEntity matchingBibEntity=new MatchingBibEntity();
        matchingBibEntity.setIsbn("isbn");
        matchingBibEntities.add(matchingBibEntity);
        return matchingBibEntities;
    }

    @Test
    public void saveMatchingMatchPointEntity() throws Exception {
        Mockito.when(matchingMatchPointsDetailsRepository.saveAll(Mockito.anyCollection())).thenReturn(new ArrayList<>());
        matchingAlgorithmProcessor.saveMatchingMatchPointEntity(Arrays.asList(new MatchingMatchPointsEntity()));
        assertNotNull(matchingAlgorithmProcessor);
    }
}