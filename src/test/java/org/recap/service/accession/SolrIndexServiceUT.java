package org.recap.service.accession;

import org.apache.camel.ProducerTemplate;
import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.recap.BaseTestCaseUT;
import org.recap.BaseTestCaseUT4;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.repository.jpa.BibliographicDetailsRepository;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

/**
 * Created by rajeshbabuk on 10/11/16.
 */



@RunWith(PowerMockRunner.class)
@PrepareForTest({SolrTemplate.class, SolrClient.class})
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*"})
public class SolrIndexServiceUT extends BaseTestCaseUT4 {

    @InjectMocks
    SolrIndexService solrIndexService;

    @Mock
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @Mock
    ProducerTemplate producerTemplate;

    @Before
    public  void setup(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(solrIndexService,"submitCollectionOwnInstBibIdListPartitionSize",1000);
    }

    @Test
    public void indexByOwnInstBibId() throws Exception {
        List<String> owningInstBibIdList=new ArrayList<>();
        owningInstBibIdList.add("1");
        List<BibliographicEntity> bibliographicEntityList=new ArrayList<>();
        bibliographicEntityList.add(getBibliographicEntity());
        Mockito.when(bibliographicDetailsRepository.findByOwningInstitutionBibIdInAndOwningInstitutionId(Mockito.anyList(),Mockito.anyInt())).thenReturn(bibliographicEntityList);
        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        SolrInputDocument solrInputDocument=new SolrInputDocument();
        Mockito.when(mocksolrTemplate1.convertBeanToSolrInputDocument(Mockito.any())).thenReturn(solrInputDocument);
        ReflectionTestUtils.setField(solrIndexService,"solrTemplate",mocksolrTemplate1);
        Mockito.when(bibliographicDetailsRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getBibliographicEntity()));
        solrIndexService.getLogger();
        solrIndexService.indexByOwnInstBibId(owningInstBibIdList,1);
        assertNotNull(owningInstBibIdList);
    }

    @Test
    public void deleteByDocId() throws Exception {
        SolrTemplate mocksolrTemplate1 = PowerMockito.mock(SolrTemplate.class);
        ReflectionTestUtils.setField(solrIndexService,"solrTemplate",mocksolrTemplate1);
        SolrClient solrClient=PowerMockito.mock(SolrClient.class);
        PowerMockito.when(mocksolrTemplate1.getSolrClient()).thenReturn(solrClient);
        UpdateResponse updateResponse=new UpdateResponse();
        Mockito.when(solrClient.deleteByQuery(Mockito.any())).thenReturn(updateResponse);
        solrIndexService.deleteByDocId("BibId",String.valueOf(1));
        solrIndexService.deleteBySolrQuery("BibId");
        assertNotNull(updateResponse);
    }

    private BibliographicEntity getBibliographicEntity() throws URISyntaxException, IOException {

        File bibContentFile = getBibContentFile();
        String sourceBibContent = FileUtils.readFileToString(bibContentFile, "UTF-8");
        File holdingsContentFile = getHoldingsContentFile();
        String sourceHoldingsContent = FileUtils.readFileToString(holdingsContentFile, "UTF-8");

        List<BibliographicEntity> bibliographicEntities = new ArrayList<>();
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent(sourceBibContent.getBytes());
        bibliographicEntity.setOwningInstitutionId(1);
        Random random = new Random();
        bibliographicEntity.setId(1);
        bibliographicEntity.setOwningInstitutionBibId(String.valueOf(1));
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setCreatedBy("tst");
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setLastUpdatedBy("tst");
        InstitutionEntity institutionEntity=new InstitutionEntity();
        institutionEntity.setId(3);
        institutionEntity.setInstitutionName("NYPL");
        institutionEntity.setInstitutionCode("NYPL");
        bibliographicEntity.setInstitutionEntity(institutionEntity);
        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent(sourceHoldingsContent.getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setCreatedBy("etl");
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setLastUpdatedBy("etl");
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setOwningInstitutionItemId(String.valueOf(random.nextInt()));
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("etl");
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setLastUpdatedBy("etl");
        String barcode = "1234";
        itemEntity.setBarcode(barcode);
        itemEntity.setCallNumber("x.12321");
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCallNumberType("1");
        itemEntity.setCustomerCode("1");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));

        holdingsEntity.setItemEntities(Arrays.asList(itemEntity));
        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));
        bibliographicEntities.add(bibliographicEntity);
        itemEntity.setBibliographicEntities(bibliographicEntities);
        return bibliographicEntity;
    }

    private File getBibContentFile() throws URISyntaxException {
        URL resource = getClass().getResource("PUL-BibContent.xml");
        return new File(resource.toURI());
    }

    private File getHoldingsContentFile() throws URISyntaxException {
        URL resource = getClass().getResource("PUL-HoldingsContent.xml");
        return new File(resource.toURI());
    }
}
