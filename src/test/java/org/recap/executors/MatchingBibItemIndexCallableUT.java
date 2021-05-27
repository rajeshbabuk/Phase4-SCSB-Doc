package org.recap.executors;

import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.BaseTestCaseUT;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.repository.jpa.BibliographicDetailsRepository;
import org.recap.repository.jpa.HoldingsDetailsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;


import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by hemalathas on 5/7/17.
 */

public class MatchingBibItemIndexCallableUT extends BaseTestCaseUT {

    private int pageNum = 1;
    private int docsPerPage = 5;
    private String coreName = "TempCore";
    @Mock
    private BibliographicDetailsRepository mockedBibliographicDetailsRepository;
    @Mock
    private HoldingsDetailsRepository holdingsDetailsRepository;
    @Mock
    private ProducerTemplate producerTemplate;
    @Mock
    private SolrTemplate solrTemplate;
    private String operationType="OngoingMatchingAlgorithm";
    private Date from=new Date();
    private Date to=new Date();

    BibliographicEntity bibliographicEntity = null;
    @Mock
    MatchingBibItemIndexExecutorService matchingBibItemIndexExecutorService;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bibliographicEntity = saveBibSingleHoldingsSingleItem();
    }

    @Test
    public void testMatchingBibItemIndexExecutorService(){
        MatchingBibItemIndexExecutorService matchingBibItemIndexExecutorService = new MatchingBibItemIndexExecutorService();
        Callable callable = matchingBibItemIndexExecutorService.getCallable(coreName, pageNum, docsPerPage,operationType, from, to);
        assertNotNull(callable);

    }

    @Test
    public void testTotalDocument(){
        Mockito.when(mockedBibliographicDetailsRepository.getCountOfBibliographicEntitiesForChangedItems(Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(Long.valueOf(1));
        MatchingBibItemIndexExecutorService matchingBibItemIndexExecutorService = new MatchingBibItemIndexExecutorService();
        matchingBibItemIndexExecutorService.setBibliographicDetailsRepository(mockedBibliographicDetailsRepository);
        int bibCountForChangedItems = matchingBibItemIndexExecutorService.getTotalDocCount(operationType, from, to);
        assertEquals(1,bibCountForChangedItems);
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
        itemEntity.setBarcode("9123");
        itemEntity.setCallNumber("x.12321");
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCallNumberType("1");
        itemEntity.setCustomerCode("123");
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("tst");
        itemEntity.setLastUpdatedBy("tst");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));
        return bibliographicEntity;
    }


    public Page<BibliographicEntity> getBibliographicPagableObject(List<BibliographicEntity> bibliographicEntityList) {
        Page<BibliographicEntity> bibliographicEntityPageObject = new Page<BibliographicEntity>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super BibliographicEntity, ? extends U> converter) {
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
            public List<BibliographicEntity> getContent() {
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
            public Iterator<BibliographicEntity> iterator() {
                return null;
            }
        };
        return bibliographicEntityPageObject;

    }






}
