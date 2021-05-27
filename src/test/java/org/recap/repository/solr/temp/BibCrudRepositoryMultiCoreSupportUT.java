package org.recap.repository.solr.temp;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.recap.BaseTestCaseUT;
import org.recap.PropertyKeyConstants;
import org.recap.model.solr.Bib;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.solr.core.SolrTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by premkb on 1/8/16.
 */

public class BibCrudRepositoryMultiCoreSupportUT extends BaseTestCaseUT {

    @Value("${" + PropertyKeyConstants.SOLR_URL + "}")
    String solrUrl;

    @Mock
    SolrTemplate solrTemplate;

    @Test
    public void instantiateBibCrudRepositoryMultiCoreSupport(){
        assertNotNull(new BibCrudRepositoryMultiCoreSupport(solrTemplate, Bib.class));
    }
}
