package org.recap.executors;

import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.recap.BaseTestCaseUT;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.repository.jpa.BibliographicDetailsRepository;
import org.recap.repository.jpa.HoldingsDetailsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.result.SolrResultPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 19/1/17.
 */
@ExtendWith(MockitoExtension.class)
public class BibItemIndexCallableUT extends BaseTestCaseUT {

    @Mock
    ProducerTemplate producerTemplate;

    @Mock
    SolrTemplate solrTemplate;

    @Mock
    HoldingsDetailsRepository holdingsDetailsRepository;

    @Mock
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @InjectMocks
    BibItemIndexCallable mockBibItemIndexCallable = new BibItemIndexCallable("","",1,1,bibliographicDetailsRepository,holdingsDetailsRepository,1,new Date(),producerTemplate,solrTemplate, null, null,new ArrayList<String>(Arrays.asList("NYPL")));




    @Test
    public void testBibItemIndexCallable() throws Exception{
        int page = 1;
        int size = 1;
        Page<BibliographicEntity> bibliographicEntities = new SolrResultPage<>(getBibliographicEntityList());
        Mockito.when(bibliographicDetailsRepository.findByOwningInstitutionIdAndLastUpdatedDateAfter(Mockito.any(),Mockito.anyInt(),Mockito.any())).thenReturn(bibliographicEntities);
        Object response = mockBibItemIndexCallable.call();
        assertNotNull(response);
    }

    private List<BibliographicEntity> getBibliographicEntityList(){
        List<BibliographicEntity> bibliographicEntityList = new ArrayList<>();
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setId(1);
        bibliographicEntity.setContent("marc content".getBytes());
        bibliographicEntity.setOwningInstitutionBibId("1");
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setCreatedBy("tst");
        bibliographicEntityList.add(bibliographicEntity);
        return bibliographicEntityList;
    }
}
