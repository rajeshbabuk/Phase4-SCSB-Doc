package org.recap.service.accession;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.junit.Test;
import org.marc4j.MarcReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.recap.RecapConstants;
import org.recap.model.accession.AccessionRequest;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.repository.jpa.BibliographicDetailsRepository;
import org.recap.repository.jpa.CustomerCodeDetailsRepository;
import org.recap.repository.jpa.ItemDetailsRepository;
import org.recap.service.authorization.NyplOauthTokenApiService;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.HoldingsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by chenchulakshmig on 20/10/16.
 */
public class AccessionServiceUT extends BaseTestCase {

    private Logger logger = LoggerFactory.getLogger(AccessionServiceUT.class);

    @Mock
    private AccessionService accessionService;

    @Value("${ils.nypl.bibdata}")
    String ilsNYPLBibData;

    @Autowired
    NyplOauthTokenApiService nyplOauthTokenApiService;

    @Mock
    BibliographicDetailsRepository mockedBibliographicDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    CustomerCodeDetailsRepository customerCodeDetailsRepository;

    @Mock
    ItemDetailsRepository mockedItemDetailsRepository;

    @Test
    public void processForPUL() throws Exception {
        List<AccessionRequest> accessionRequestList = new ArrayList<>();
        AccessionRequest accessionRequest = new AccessionRequest();
        accessionRequest.setCustomerCode("PB");
        accessionRequest.setItemBarcode("32101062128309");
        accessionRequestList.add(accessionRequest);
        accessionService.processRequest(accessionRequestList);
        Mockito.when(mockedBibliographicDetailsRepository.findByOwningInstitutionBibId("202304")).thenReturn(Arrays.asList(saveBibSingleHoldingsSingleItem("32101062128309","PB","callnumber")));
        List<BibliographicEntity> fetchedBibliographicEntityList = mockedBibliographicDetailsRepository.findByOwningInstitutionBibId("202304");
        String updatedBibMarcXML = new String(fetchedBibliographicEntityList.get(0).getContent(), StandardCharsets.UTF_8);
        List<Record> bibRecordList = readMarcXml(updatedBibMarcXML);
        assertNotNull(bibRecordList);
        HoldingsEntity holdingsEntity = fetchedBibliographicEntityList.get(0).getHoldingsEntities().get(0);
        String updatedHoldingMarcXML = new String(holdingsEntity.getContent(),StandardCharsets.UTF_8);
        List<Record> holdingRecordList = readMarcXml(updatedHoldingMarcXML);
        logger.info("updatedHoldingMarcXML-->"+updatedHoldingMarcXML);
        TestCase.assertNotNull(holdingRecordList);
        DataField field852 = (DataField)holdingRecordList.get(0).getVariableField("852");
        assertEquals("JFL 81-165", field852.getSubfield('h').getData());
    }

    public void deleteByDocId(String docIdParam, String docIdValue) throws IOException, SolrServerException {
        UpdateResponse updateResponse = solrTemplate.getSolrClient().deleteByQuery(docIdParam+":"+docIdValue);
        solrTemplate.commit();
    }

    @Test
    public void accessionUnavilableBarcode() throws Exception {
        List<AccessionRequest> accessionRequestList = new ArrayList<>();
        AccessionRequest accessionRequest = new AccessionRequest();
        accessionRequest.setCustomerCode("PA");
        accessionRequest.setItemBarcode("3210106212830");
        accessionRequestList.add(accessionRequest);
        accessionService.processRequest(accessionRequestList);
        List<ItemEntity> itemEntityList = saveBibSingleHoldingsSingleItem("3210106212830","PA","dummycallnumber").getItemEntities();
        Mockito.when(mockedItemDetailsRepository.findByBarcode("3210106212830")).thenReturn(itemEntityList);
        List<ItemEntity> itemEntities = mockedItemDetailsRepository.findByBarcode("3210106212830");
        assertNotNull(itemEntities);
        assertTrue(itemEntities.size() > 0);
        assertNotNull(itemEntities.get(0));
        assertEquals("dummycallnumber",itemEntities.get(0).getCallNumber());
    }

    @Test
    public void accessionUnavilableBarcodeAvoidDuplicate() throws Exception {
        List<AccessionRequest> accessionRequestList = new ArrayList<>();
        AccessionRequest accessionRequest = new AccessionRequest();
        accessionRequest.setCustomerCode("PA");
        accessionRequest.setItemBarcode("3210106212830");
        accessionRequestList.add(accessionRequest);
        accessionService.processRequest(accessionRequestList);
        List<ItemEntity> itemEntityList = saveBibSingleHoldingsSingleItem("3210106212830","PA","dummycallnumber").getItemEntities();
        Mockito.when(mockedItemDetailsRepository.findByBarcode("3210106212830")).thenReturn(itemEntityList);
        List<ItemEntity> itemEntities = mockedItemDetailsRepository.findByBarcode("3210106212830");
        assertNotNull(itemEntities);
        assertTrue(itemEntities.size() > 0);
        assertNotNull(itemEntities.get(0));
        assertEquals(1,itemEntities.get(0).getBibliographicEntities().size());

        Mockito.when(accessionService.processRequest(accessionRequestList)).thenReturn("3210106212830"+RecapConstants.HYPHEN+RecapConstants.ITEM_BARCODE_ALREADY_ACCESSIONED_MSG);
        String respose = accessionService.processRequest(accessionRequestList);
        assertEquals("3210106212830"+RecapConstants.HYPHEN+RecapConstants.ITEM_BARCODE_ALREADY_ACCESSIONED_MSG,respose);
        Mockito.when(mockedItemDetailsRepository.findByBarcode("3210106212830")).thenReturn(itemEntityList);
        List<ItemEntity> itemEntities1 = mockedItemDetailsRepository.findByBarcode("3210106212830");
        assertNotNull(itemEntities1);
        assertTrue(itemEntities1.size() > 0);
        assertNotNull(itemEntities1.get(0));
        assertEquals(1,itemEntities1.get(0).getBibliographicEntities().size());

    }

    @Test
    public void processForNYPL() throws Exception {
        List<AccessionRequest> accessionRequestList = new ArrayList<>();
        AccessionRequest accessionRequest = new AccessionRequest();
        accessionRequest.setCustomerCode("NA");
        accessionRequest.setItemBarcode("33433002031718");
        accessionRequestList.add(accessionRequest);
        accessionService.processRequest(accessionRequestList);
        Mockito.when(mockedBibliographicDetailsRepository.findByOwningInstitutionBibId(".b100000186")).thenReturn(Arrays.asList(saveBibSingleHoldingsSingleItem("33433002031718","NA","callnumber")));
        List<BibliographicEntity> fetchedBibliographicEntityList = mockedBibliographicDetailsRepository.findByOwningInstitutionBibId(".b100000186");
        String updatedBibMarcXML = new String(fetchedBibliographicEntityList.get(0).getContent(), StandardCharsets.UTF_8);
        List<Record> bibRecordList = readMarcXml(updatedBibMarcXML);
        assertNotNull(bibRecordList);
        HoldingsEntity holdingsEntity = fetchedBibliographicEntityList.get(0).getHoldingsEntities().get(0);
        String updatedHoldingMarcXML = new String(holdingsEntity.getContent(),StandardCharsets.UTF_8);
        List<Record> holdingRecordList = readMarcXml(updatedHoldingMarcXML);
        logger.info("updatedHoldingMarcXML-->"+updatedHoldingMarcXML);
        TestCase.assertNotNull(holdingRecordList);
        DataField field852 = (DataField)holdingRecordList.get(0).getVariableField("852");
        assertEquals("JFL 81-165", field852.getSubfield('h').getData());
    }

    private List<Record> readMarcXml(String marcXmlString) {
        List<Record> recordList = new ArrayList<>();
        InputStream in = new ByteArrayInputStream(marcXmlString.getBytes());
        MarcReader reader = new MarcXmlReader(in);
        while (reader.hasNext()) {
            Record record = reader.next();
            recordList.add(record);
            logger.info(record.toString());
        }
        return recordList;
    }

    @Test
    public void getOwningInstitution() throws Exception {
        String customerCode = "PB";
        Mockito.when(accessionService.getCustomerCodeDetailsRepository()).thenReturn(customerCodeDetailsRepository);
        Mockito.when(accessionService.getOwningInstitution(customerCode)).thenCallRealMethod();
        String owningInstitution = accessionService.getOwningInstitution(customerCode);
        assertNotNull(owningInstitution);
        assertTrue(owningInstitution.equalsIgnoreCase(RecapConstants.PRINCETON));
    }

    public BibliographicEntity saveBibSingleHoldingsSingleItem(String itemBarcode,String customerCode, String callnumber) throws Exception {
        File bibContentFile = getBibContentFile();
        File holdingsContentFile = getHoldingsContentFile();
        String sourceBibContent = FileUtils.readFileToString(bibContentFile, "UTF-8");
        String sourceHoldingsContent = FileUtils.readFileToString(holdingsContentFile, "UTF-8");

        Random random = new Random();
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent(sourceBibContent.getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setCreatedBy("tst");
        bibliographicEntity.setLastUpdatedBy("tst");
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setOwningInstitutionBibId(String.valueOf(random.nextInt()));
        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent(sourceHoldingsContent.getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setCreatedBy("tst");
        holdingsEntity.setLastUpdatedBy("tst");
        holdingsEntity.setOwningInstitutionId(1);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        ItemEntity itemEntity = getItemEntity(itemBarcode,customerCode,callnumber);
        itemEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        itemEntity.setBibliographicEntities(Arrays.asList(bibliographicEntity));

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));

        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);
        return savedBibliographicEntity;

    }

    public ItemEntity getItemEntity(String itemBarcode,String customerCode,String callnumber){
        Random random = new Random();
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setOwningInstitutionItemId(String.valueOf(random.nextInt()));
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setBarcode(itemBarcode);
        itemEntity.setCallNumber(callnumber);
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCallNumberType("1");
        itemEntity.setCustomerCode(customerCode);
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("tst");
        itemEntity.setLastUpdatedBy("tst");
        itemEntity.setItemAvailabilityStatusId(1);
        return itemEntity;
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