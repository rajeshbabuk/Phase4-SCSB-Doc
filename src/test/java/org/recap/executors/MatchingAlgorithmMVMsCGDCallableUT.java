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
import org.recap.matchingalgorithm.MatchingCounter;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 5/7/17.
 */
public class MatchingAlgorithmMVMsCGDCallableUT extends BaseTestCaseUT4 {

    @Mock
    private ReportDataDetailsRepository reportDataDetailsRepository;
    @Mock
    private BibliographicDetailsRepository mockedBibliographicDetailsRepository;
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
    @Mock
    private Map collectionGroupMap;
    @Mock
    MatchingCounter matchingCounter;

    long from = Long.valueOf(0);
    int pageNum = 1;
    Integer batchSize = 10;
    BibliographicEntity bibliographicEntity = null;
    int collectionGroupId = 0;
    @Mock
    InstitutionDetailsRepository institutionDetailsRepository;


    public MatchingAlgorithmMVMsCGDCallableUT() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        from = pageNum * Long.valueOf(batchSize);
       // MatchingCounter.reset();
        List<String> scsbInstitutions=Arrays.asList("HTC");
        ReflectionTestUtils.setField(matchingCounter,"scsbInstitutions",scsbInstitutions);
        bibliographicEntity = saveBibSingleHoldingsSingleItem();
        collectionGroupId = bibliographicEntity.getItemEntities().get(0).getCollectionGroupId();
        Mockito.when(reportDataDetailsRepository.getReportDataEntityForMatchingMVMs(ScsbCommonConstants.BIB_ID, from, batchSize)).thenReturn(getReportDataEntity(bibliographicEntity.getId()));
        Mockito.when((Integer) collectionGroupMap.get(ScsbCommonConstants.REPORTS_OPEN)).thenReturn(2);
        Mockito.when(collectionGroupMap.get(ScsbCommonConstants.SHARED_CGD)).thenReturn(1);
        Mockito.when(mockedBibliographicDetailsRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(bibliographicEntity));
    }

    public List<ReportDataEntity> getReportDataEntity(Integer bibId){
        List<ReportDataEntity> reportDataEntityList = new ArrayList<>();
        ReportDataEntity reportDataEntity = new ReportDataEntity();
        reportDataEntity.setHeaderValue(bibId.toString());
        reportDataEntityList.add(reportDataEntity);
        return reportDataEntityList;
    }

    @Test
    public void testMatchingAlgorithmMVMsCGDCallable() throws Exception {
        Map institutionMap = new HashMap();
        MatchingAlgorithmMVMsCGDCallable matchingAlgorithmMVMsCGDCallable = new MatchingAlgorithmMVMsCGDCallable(reportDataDetailsRepository, mockedBibliographicDetailsRepository,pageNum,batchSize,producerTemplate,
                collectionGroupMap,institutionMap,itemChangeLogDetailsRepository,collectionGroupDetailsRepository,itemDetailsRepository,institutionDetailsRepository);
        Object object = matchingAlgorithmMVMsCGDCallable.call();
        assertEquals(1,collectionGroupId);
        BibliographicEntity afterUpdate = mockedBibliographicDetailsRepository.findById(bibliographicEntity.getId()).orElse(null);
        assertNotNull(afterUpdate);
    }

    public BibliographicEntity saveBibSingleHoldingsSingleItem() throws Exception {

        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("UC");
        institutionEntity.setInstitutionName("University of Chicago");

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
        itemEntity.setBarcode("123");
        itemEntity.setCallNumber("x.12321");
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCallNumberType("1");
        itemEntity.setCustomerCode("123");
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("tst");
        itemEntity.setLastUpdatedBy("tst");
        itemEntity.setCatalogingStatus("Complete");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));
        return bibliographicEntity;
    }




}
