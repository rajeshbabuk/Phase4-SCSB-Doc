package org.recap.controller;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.BaseTestCaseUT;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.repository.jpa.ItemDetailsRepository;
import org.recap.util.UpdateCgdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by hemalathas on 25/1/17.
 */
public class UpdateItemStatusControllerUT extends BaseTestCaseUT {

    private static final Logger logger = LoggerFactory.getLogger(UpdateItemStatusControllerUT.class);

    @InjectMocks
    UpdateItemStatusController updateItemStatusController;

    @Mock
    ItemDetailsRepository itemDetailsRepository;

    @Mock
    UpdateCgdUtil updateCgdUtil;

    @BeforeEach
    public void setup()throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateCgdForItem() throws Exception {
        String itemBarcode = saveBibSingleHoldingsSingleItem().getItemEntities().get(0).getBarcode();
        Mockito.when(itemDetailsRepository.findByBarcode(itemBarcode)).thenReturn(saveBibSingleHoldingsSingleItem().getItemEntities());
        String status = updateItemStatusController.updateCgdForItem(itemBarcode);
        assertNotNull(status);
        assertEquals("Solr Indexing Successful",status);
    }

    @Test
    public void testUpdateCgdForItemException() throws Exception {
        String itemBarcode = saveBibSingleHoldingsSingleItem().getItemEntities().get(0).getBarcode();
        Mockito.when(itemDetailsRepository.findByBarcode(itemBarcode)).thenThrow(NullPointerException.class);
        updateItemStatusController.getLogger();
        String status = updateItemStatusController.updateCgdForItem(itemBarcode);
        assertNotNull(status);
        assertEquals("Solr Indexing Failed",status);
    }

    public BibliographicEntity saveBibSingleHoldingsSingleItem() throws Exception {
        Random random = new Random();
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        File holdingsContentFile = getHoldingsContentFile();
        File bibContentFile = getBibContentFile();
        String sourceBibContent = FileUtils.readFileToString(bibContentFile, "UTF-8");
        String sourceHoldingContent = FileUtils.readFileToString(holdingsContentFile, "UTF-8");
        bibliographicEntity.setContent(sourceBibContent.getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setCreatedBy("tst");
        bibliographicEntity.setLastUpdatedBy("tst");
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setOwningInstitutionBibId(String.valueOf(random.nextInt()));
        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent(sourceHoldingContent.getBytes());
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
        itemEntity.setBarcode("3894123");
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

    private File getBibContentFile() throws URISyntaxException {
        URL resource = getClass().getResource("BibContent.xml");
        return new File(resource.toURI());
    }
    private File getHoldingsContentFile() throws URISyntaxException {
        URL resource = getClass().getResource("HoldingsContent.xml");
        return new File(resource.toURI());
    }


}