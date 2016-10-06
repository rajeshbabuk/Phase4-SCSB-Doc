package org.recap.executors;

import org.apache.solr.common.SolrInputDocument;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.repository.jpa.BibliographicDetailsRepository;
import org.recap.repository.jpa.HoldingsDetailsRepository;
import org.recap.util.BibJSONUtil;
import org.springframework.data.solr.core.SolrTemplate;

import java.util.concurrent.Callable;

/**
 * Created by chenchulakshmig on 21/6/16.
 */
public class BibItemRecordSetupCallable implements Callable {

    BibliographicEntity bibliographicEntity;
    private final SolrTemplate solrTemplate;
    BibliographicDetailsRepository bibliographicDetailsRepository;
    HoldingsDetailsRepository holdingsDetailsRepository;


    public BibItemRecordSetupCallable(BibliographicEntity bibliographicEntity, SolrTemplate solrTemplate, BibliographicDetailsRepository bibliographicDetailsRepository,
                                      HoldingsDetailsRepository holdingsDetailsRepository) {
        this.bibliographicEntity = bibliographicEntity;
        this.solrTemplate = solrTemplate;
        this.bibliographicDetailsRepository = bibliographicDetailsRepository;
        this.holdingsDetailsRepository = holdingsDetailsRepository;
    }

    @Override
    public Object call() throws Exception {
        SolrInputDocument solrInputDocument = new BibJSONUtil().generateBibAndItemsForIndex(bibliographicEntity, solrTemplate, bibliographicDetailsRepository, holdingsDetailsRepository);
        return solrInputDocument ;
    }
}
