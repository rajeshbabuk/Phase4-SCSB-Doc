package org.recap.executors;

import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.BaseTestCaseUT;
import org.recap.BaseTestCaseUT4;
import org.recap.ScsbCommonConstants;
import org.recap.matchingalgorithm.MatchingAlgorithmCGDProcessor;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.model.jpa.ReportDataEntity;
import org.recap.repository.jpa.BibliographicDetailsRepository;
import org.recap.repository.jpa.CollectionGroupDetailsRepository;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.recap.repository.jpa.ItemChangeLogDetailsRepository;
import org.recap.repository.jpa.ItemDetailsRepository;
import org.recap.repository.jpa.ReportDataDetailsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 5/7/17.
 */
public class MatchingAlgorithmMonographCGDCallableUT extends BaseTestCaseUT4 {

    @Mock
    private ReportDataDetailsRepository reportDataDetailsRepository;
    @Mock
    private BibliographicDetailsRepository bibliographicDetailsRepository;

    @Mock
    private InstitutionDetailsRepository institutionDetailsRepository;
    @Mock
    private ItemChangeLogDetailsRepository itemChangeLogDetailsRepository;
    @Mock
    private CollectionGroupDetailsRepository collectionGroupDetailsRepository;
    @Mock
    private ItemDetailsRepository itemDetailsRepository;
    @Mock
    private MatchingAlgorithmCGDProcessor matchingAlgorithmCGDProcessor;
    @Mock
    ProducerTemplate producerTemplate;

    long from = Long.valueOf(0);
    int pageNum = 1;
    Integer batchSize = 10;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        from = pageNum * Long.valueOf(batchSize);
        Mockito.when(reportDataDetailsRepository.getReportDataEntityForPendingMatchingMonographs(ScsbCommonConstants.BIB_ID, from, batchSize)).thenReturn(getReportDataEntity());
        Mockito.when(reportDataDetailsRepository.getReportDataEntityForMatchingMonographs(ScsbCommonConstants.BIB_ID, from, batchSize)).thenReturn(getReportDataEntity());
    }

    @Test
    public void testMatchingAlgorithmMonographCGDCallable() throws Exception {
        Map collectionGroupMap = new HashMap();
        Map institutionMap = new HashMap();
        MatchingAlgorithmMonographCGDCallable matchingAlgorithmMonographCGDCallable = new MatchingAlgorithmMonographCGDCallable(reportDataDetailsRepository,bibliographicDetailsRepository,pageNum,batchSize,producerTemplate,
                                                                                        collectionGroupMap,institutionMap,itemChangeLogDetailsRepository,collectionGroupDetailsRepository,itemDetailsRepository,true, institutionDetailsRepository);
        Object object = matchingAlgorithmMonographCGDCallable.call();
        assertNotNull(object);
    }

    @Test
    public void testMatchingAlgorithmMonographCGDCallableelse() throws Exception {
        Map collectionGroupMap = new HashMap();
        Map institutionMap = new HashMap();
        institutionMap.put("NYPL",3);
        Mockito.when(bibliographicDetailsRepository.findByIdIn(Mockito.any())).thenReturn(Arrays.asList(saveBibSingleHoldingsSingleItem()));
        MatchingAlgorithmMonographCGDCallable matchingAlgorithmMonographCGDCallable = new MatchingAlgorithmMonographCGDCallable(reportDataDetailsRepository,bibliographicDetailsRepository,pageNum,batchSize,producerTemplate,
                collectionGroupMap,institutionMap,itemChangeLogDetailsRepository,collectionGroupDetailsRepository,itemDetailsRepository,true, institutionDetailsRepository);
        Object object = matchingAlgorithmMonographCGDCallable.call();
        assertNotNull(object);
    }

    @Test
    public void testMatchingAlgorithmMonographCGDCallableelseandif() throws Exception {
        Map collectionGroupMap = new HashMap();
        collectionGroupMap.put(ScsbCommonConstants.SHARED_CGD,1);
        Map institutionMap = new HashMap();
        institutionMap.put("NYPL",3);
        Mockito.when(bibliographicDetailsRepository.findByIdIn(Mockito.any())).thenReturn(Arrays.asList(saveBibSingleHoldingsSingleItem()));
        MatchingAlgorithmMonographCGDCallable matchingAlgorithmMonographCGDCallable = new MatchingAlgorithmMonographCGDCallable(reportDataDetailsRepository,bibliographicDetailsRepository,pageNum,batchSize,producerTemplate,
                collectionGroupMap,institutionMap,itemChangeLogDetailsRepository,collectionGroupDetailsRepository,itemDetailsRepository,true, institutionDetailsRepository);
        Object object = matchingAlgorithmMonographCGDCallable.call();
        assertNotNull(object);
    }

    @Test
    public void testMatchingAlgorithmMonographCGDCallableisPendingMatch() throws Exception {
        Map collectionGroupMap = new HashMap();
        Map institutionMap = new HashMap();

        MatchingAlgorithmMonographCGDCallable matchingAlgorithmMonographCGDCallable = new MatchingAlgorithmMonographCGDCallable(reportDataDetailsRepository,bibliographicDetailsRepository,pageNum,batchSize,producerTemplate,
                collectionGroupMap,institutionMap,itemChangeLogDetailsRepository,collectionGroupDetailsRepository,itemDetailsRepository,false, institutionDetailsRepository);
        Object object = matchingAlgorithmMonographCGDCallable.call();
        assertNotNull(object);
    }

    public List<ReportDataEntity> getReportDataEntity(){
        List<ReportDataEntity> reportDataEntityList = new ArrayList<>();
        ReportDataEntity reportDataEntity = new ReportDataEntity();
        reportDataEntity.setHeaderValue("1134");
        reportDataEntity.setRecordNum("1");
        reportDataEntityList.add(reportDataEntity);
        return reportDataEntityList;
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
        bibliographicEntity.setOwningInstitutionId(3);
        bibliographicEntity.setOwningInstitutionBibId(String.valueOf(random.nextInt()));

        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setCreatedBy("tst");
        holdingsEntity.setLastUpdatedBy("tst");
        holdingsEntity.setOwningInstitutionId(3);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setOwningInstitutionItemId(String.valueOf(random.nextInt()));
        itemEntity.setOwningInstitutionId(3);
        itemEntity.setBarcode("512356");
        itemEntity.setCallNumber("x.12321");
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCallNumberType("1");
        itemEntity.setCustomerCode("123");
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("tst");
        itemEntity.setLastUpdatedBy("tst");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setCatalogingStatus(ScsbCommonConstants.COMPLETE_STATUS);
        itemEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        ItemEntity itemEntity1 = itemEntity;
        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity,itemEntity1));
        return bibliographicEntity;
    }

}
