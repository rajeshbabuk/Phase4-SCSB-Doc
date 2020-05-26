package org.recap.repository.jpa;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.BibliographicPK;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by pvsubrah on 6/21/16.
 */
public class BibliographicDetailsRepositoryUT extends BaseTestCase {

    @Autowired
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    HoldingsDetailsRepository holdingsDetailsRepository;

    @Autowired
    ItemDetailsRepository itemDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DateUtil dateUtil;

    @Test
    public void saveAndFindBibHoldingsItemEntity() throws Exception {

        assertNotNull(bibliographicDetailsRepository);
        assertNotNull(holdingsDetailsRepository);
        assertNotNull(entityManager);

        Random random = new Random();

        String owningInstitutionBibId = String.valueOf(random.nextInt());
        int owningInstitutionId = 1;

        Page<BibliographicEntity> byOwningInstitutionId = bibliographicDetailsRepository.findByOwningInstitutionIdAndIsDeletedFalse(PageRequest.of(0, 10), owningInstitutionId);

        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("Mock Bib Content".getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setCreatedBy("etl");
        bibliographicEntity.setLastUpdatedBy("etl");
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setOwningInstitutionBibId(owningInstitutionBibId);
        bibliographicEntity.setOwningInstitutionId(owningInstitutionId);

        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setCreatedBy("etl");
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setLastUpdatedBy("etl");
        holdingsEntity.setOwningInstitutionId(owningInstitutionId);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setCallNumberType("0");
        itemEntity.setCallNumber("callNum");
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("etl");
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setLastUpdatedBy("etl");
        itemEntity.setBarcode("12310");
        itemEntity.setOwningInstitutionItemId(".i1231");
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCustomerCode("PA");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));

        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);

        assertNotNull(savedBibliographicEntity);
        assertNotNull(savedBibliographicEntity.getBibliographicId());

        Long countByOwningInstitutionIdAfterAdd = bibliographicDetailsRepository.countByOwningInstitutionIdAndIsDeletedFalse(owningInstitutionId);
        assertTrue(countByOwningInstitutionIdAfterAdd > byOwningInstitutionId.getTotalElements());

        List<BibliographicEntity> byOwningInstitutionBibId = bibliographicDetailsRepository.findByOwningInstitutionBibId(owningInstitutionBibId);
        assertNotNull(byOwningInstitutionBibId);
        assertTrue(byOwningInstitutionBibId.size() > 0);

        BibliographicEntity byOwningInstitutionIdAndOwningInstitutionBibId = bibliographicDetailsRepository.findByOwningInstitutionIdAndOwningInstitutionBibIdAndIsDeletedFalse(owningInstitutionId, owningInstitutionBibId);
        assertNotNull(byOwningInstitutionIdAndOwningInstitutionBibId);

        BibliographicPK bibliographicPK = new BibliographicPK();
        bibliographicPK.setOwningInstitutionId(owningInstitutionId);
        bibliographicPK.setOwningInstitutionBibId(owningInstitutionBibId);
        BibliographicEntity entity = bibliographicDetailsRepository.getOne(bibliographicPK);
        assertNotNull(entity);

        assertNotNull(holdingsDetailsRepository);
        HoldingsEntity savedHoldingsEntity = savedBibliographicEntity.getHoldingsEntities().get(0);
        assertNotNull(savedHoldingsEntity);
        assertNotNull(savedHoldingsEntity.getHoldingsId());

        HoldingsEntity byHoldingsId = holdingsDetailsRepository.findByHoldingsId(savedHoldingsEntity.getHoldingsId());
        assertNotNull(byHoldingsId);

        assertNotNull(itemDetailsRepository);
        ItemEntity savedItemEntity = savedBibliographicEntity.getItemEntities().get(0);
        assertNotNull(savedItemEntity);
        assertNotNull(savedItemEntity.getItemId());

        ItemEntity byItemId = itemDetailsRepository.findByItemId(savedItemEntity.getItemId());
        assertNotNull(byItemId);
    }

    @Test
    public void countByOwningInstitutionCode() throws Exception {
        Long bibCount = bibliographicDetailsRepository.countByOwningInstitutionCodeAndIsDeletedFalse("PUL");
        assertNotNull(bibCount);
    }

    @Test
    public void findByLastUpdatedDateAfter() throws Exception {
        Date fromDate = DateUtils.addDays(new Date(), -1);
        Page<BibliographicEntity> byCreatedDateAfterAndIsDeletedFalse = bibliographicDetailsRepository.findByLastUpdatedDateAfter(PageRequest.of(0, 10), fromDate);
        assertNotNull(byCreatedDateAfterAndIsDeletedFalse);
    }

    @Test
    public void findByOwningInstitutionIdAndLastUpdatedDateAfter() throws Exception {
        Date fromDate = DateUtils.addDays(new Date(), -1);
        Page<BibliographicEntity> byCreatedDateAfterAndIsDeletedFalse = bibliographicDetailsRepository.findByOwningInstitutionIdAndLastUpdatedDateAfter(PageRequest.of(0, 10), 1, fromDate);
        assertNotNull(byCreatedDateAfterAndIsDeletedFalse);
    }

    @Test
    public void findCountByBibIdRangesTest() throws Exception {

        saveAndFindBibHoldingsItemEntity();

        List<Integer> bibIds = Arrays.asList(1,2,3);
        Integer fromBibId = 1;
        Integer toBibId = 10;

        Long countOfBibBasedOnBibId = bibliographicDetailsRepository.getCountOfBibBasedOnBibIds(bibIds);
        assertNotNull(countOfBibBasedOnBibId);
        assertTrue(countOfBibBasedOnBibId > 0);

        Long countOfBibBasedOnBibIdRange = bibliographicDetailsRepository.getCountOfBibBasedOnBibIdRange(fromBibId, toBibId);
        assertNotNull(countOfBibBasedOnBibIdRange);
        assertTrue(countOfBibBasedOnBibIdRange > 0);

        Long countOfBibBasedOnBibIdRangeAndInst = bibliographicDetailsRepository.getCountOfBibBasedOnDateRange(dateUtil.getFromDate(new Date()), dateUtil.getToDate(new Date()));
        assertNotNull(countOfBibBasedOnBibIdRangeAndInst);
        assertTrue(countOfBibBasedOnBibIdRangeAndInst > 0);
    }

    @Test
    public void findBibsByBibIdRangesTest() throws Exception {

        saveAndFindBibHoldingsItemEntity();

        List<Integer> bibIds = Arrays.asList(1,2,3);
        Integer fromBibId = 1;
        Integer toBibId = 10;

        Page<BibliographicEntity> bibsBasedOnBibId = bibliographicDetailsRepository.getBibsBasedOnBibIds(PageRequest.of(0, 10), bibIds);
        assertNotNull(bibsBasedOnBibId);
        assertTrue(bibsBasedOnBibId.getContent().size() > 0);

        Page<BibliographicEntity> bibsBasedOnBibIdRange = bibliographicDetailsRepository.getBibsBasedOnBibIdRange(PageRequest.of(0, 10), fromBibId, toBibId);
        assertNotNull(bibsBasedOnBibIdRange);
        assertTrue(bibsBasedOnBibIdRange.getContent().size() > 0);

        Page<BibliographicEntity> bibsBasedOnBibIdRangeAndInst = bibliographicDetailsRepository.getBibsBasedOnDateRange(PageRequest.of(0, 10), dateUtil.getFromDate(new Date()), dateUtil.getToDate(new Date()));
        assertNotNull(bibsBasedOnBibIdRangeAndInst);
        assertTrue(bibsBasedOnBibIdRangeAndInst.getContent().size() > 0);
    }

}