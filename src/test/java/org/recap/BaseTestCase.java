package org.recap;

import org.apache.solr.client.solrj.SolrClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.recap.admin.SolrAdmin;
import org.recap.repository.jpa.BibliographicDetailsRepository;
import org.recap.repository.jpa.CollectionGroupDetailsRepository;
import org.recap.repository.jpa.CustomerCodeDetailsRepository;
import org.recap.repository.jpa.HoldingsDetailsRepository;
import org.recap.repository.jpa.ItemStatusDetailsRepository;
import org.recap.repository.jpa.PermissionsRepository;
import org.recap.repository.jpa.RequestItemDetailsRepository;
import org.recap.repository.jpa.RolesDetailsRepositorty;
import org.recap.repository.jpa.UserDetailsRepository;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.recap.repository.jpa.ItemDetailsRepository;
import org.recap.repository.jpa.ItemChangeLogDetailsRepository;
import org.recap.repository.solr.main.BibSolrCrudRepository;
import org.recap.repository.solr.main.BibSolrDocumentRepository;
import org.recap.repository.solr.main.HoldingsSolrCrudRepository;
import org.recap.repository.solr.main.ItemCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = Main.class)
@WebAppConfiguration
@Transactional
@Rollback()
public class BaseTestCase {

    @Autowired
    public SolrAdmin solrAdmin;

    @Autowired
    public SolrClient solrAdminClient;

    @Autowired
    public BibSolrCrudRepository bibSolrCrudRepository;

    @Autowired
    public HoldingsSolrCrudRepository holdingsSolrCrudRepository;

    @Autowired
    public BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    public ItemDetailsRepository itemDetailsRepository;

    @Autowired
    public HoldingsDetailsRepository holdingDetailRepository;

    @Autowired
    public ItemStatusDetailsRepository itemStatusDetailsRepository;

    @Autowired
    public InstitutionDetailsRepository institutionDetailRepository;

    @Autowired
    public CollectionGroupDetailsRepository collectionGroupDetailRepository;

    @Autowired
    public ItemChangeLogDetailsRepository itemChangeLogDetailsRepository;

    @Autowired
    public CustomerCodeDetailsRepository customerCodeDetailsRepository;

    @Autowired
    public RequestItemDetailsRepository requestItemDetailsRepository;

    @Autowired
    public ItemCrudRepository itemCrudRepository;

    @Autowired
    BibSolrDocumentRepository bibSolrDocumentRepository;

    @Autowired
    public SolrTemplate solrTemplate;

    @Autowired
    public UserDetailsRepository userRepo;

    @Autowired
    public RolesDetailsRepositorty roleRepository;

    @Autowired
    public PermissionsRepository permissionsRepository;


    @Test
    public void loadContexts() {
        System.out.println();
    }
}