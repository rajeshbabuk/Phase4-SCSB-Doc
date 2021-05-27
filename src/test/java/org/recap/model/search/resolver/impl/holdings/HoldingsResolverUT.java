package org.recap.model.search.resolver.impl.holdings;

import org.junit.jupiter.api.Test;
import org.recap.BaseTestCaseUT;
import org.recap.ScsbCommonConstants;
import org.recap.model.solr.Holdings;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Anithav on 16/06/20.
 */

public class HoldingsResolverUT extends BaseTestCaseUT {
    Holdings holdings=new Holdings();
    Date date=new Date();

    @Test
    public void testIsDeletedHoldingsValueResolver() throws Exception {
        IsDeletedHoldingsValueResolver isDeletedHoldingsValueResolver = new IsDeletedHoldingsValueResolver();
        isDeletedHoldingsValueResolver.isInterested(ScsbCommonConstants.IS_DELETED_HOLDINGS);
        isDeletedHoldingsValueResolver.setValue(holdings,true);
        assertNotNull(isDeletedHoldingsValueResolver.getClass());
    }

    @Test
    public void testSummaryHoldingsValueResolver() throws Exception {
        SummaryHoldingsValueResolver summaryHoldingsValueResolver = new SummaryHoldingsValueResolver();
        summaryHoldingsValueResolver.isInterested("SummaryHoldings");
        summaryHoldingsValueResolver.setValue(holdings,"true");
        assertNotNull(summaryHoldingsValueResolver.getClass());
    }
    @Test
    public void testHoldingsLastUpdatedByValueResolver() throws Exception {
        HoldingsLastUpdatedByValueResolver holdingsLastUpdatedByValueResolver = new HoldingsLastUpdatedByValueResolver();
        holdingsLastUpdatedByValueResolver.isInterested("HoldingsLastUpdatedBy");
        holdingsLastUpdatedByValueResolver.setValue(holdings,"true");
        assertNotNull(holdingsLastUpdatedByValueResolver.getClass());
    }
    @Test
    public void testHoldingsCreatedDateValueResolver() throws Exception {
        HoldingsCreatedDateValueResolver holdingsCreatedDateValueResolver = new HoldingsCreatedDateValueResolver();
        holdingsCreatedDateValueResolver.isInterested("HoldingsCreatedDate");
        holdingsCreatedDateValueResolver.setValue(holdings,date);
        assertNotNull(holdingsCreatedDateValueResolver.getClass());
    }
    @Test
    public void testHoldingsIdValueResolver() throws Exception {
        HoldingsIdValueResolver holdingsIdValueResolver = new HoldingsIdValueResolver();
        holdingsIdValueResolver.isInterested(ScsbCommonConstants.HOLDING_ID);
        holdingsIdValueResolver.setValue(holdings,1);
        assertNotNull(holdingsIdValueResolver.getClass());
    }
    @Test
    public void testOwningInstitutionHoldingsIdValueResolver() throws Exception {
        OwningInstitutionHoldingsIdValueResolver owningInstitutionHoldingsIdValueResolver = new OwningInstitutionHoldingsIdValueResolver();
        owningInstitutionHoldingsIdValueResolver.isInterested(ScsbCommonConstants.OWNING_INSTITUTION_HOLDINGS_ID);
        owningInstitutionHoldingsIdValueResolver.setValue(holdings,"true");
        assertNotNull(owningInstitutionHoldingsIdValueResolver.getClass());
    }
    @Test
    public void testDocTypeValueResolver() throws Exception {
        DocTypeValueResolver docTypeValueResolver = new DocTypeValueResolver();
        docTypeValueResolver.isInterested("DocType");
        docTypeValueResolver.setValue(holdings,"true");
        assertNotNull(docTypeValueResolver.getClass());
    }
    @Test
    public void testHoldingsRootValueResolver() throws Exception {
        HoldingsRootValueResolver holdingsRootValueResolver = new HoldingsRootValueResolver();
        holdingsRootValueResolver.isInterested("_root_");
        holdingsRootValueResolver.setValue(holdings,"true");
        assertNotNull(holdingsRootValueResolver.getClass());
    }
    @Test
    public void testHoldingsCreatedByValueResolver() throws Exception {
        HoldingsCreatedByValueResolver holdingsCreatedByValueResolver = new HoldingsCreatedByValueResolver();
        holdingsCreatedByValueResolver.isInterested("HoldingsCreatedBy");
        holdingsCreatedByValueResolver.setValue(holdings,"true");
        assertNotNull(holdingsCreatedByValueResolver.getClass());
    }
    @Test
    public void testHoldingsLastUpdatedDateValueResolver() throws Exception {
        HoldingsLastUpdatedDateValueResolver holdingsLastUpdatedDateValueResolver = new HoldingsLastUpdatedDateValueResolver();
        holdingsLastUpdatedDateValueResolver.isInterested("HoldingsLastUpdatedDate");
        holdingsLastUpdatedDateValueResolver.setValue(holdings,date);
        assertNotNull(holdingsLastUpdatedDateValueResolver.getClass());
    }
    @Test
    public void testIdValueResolver() throws Exception {
        IdValueResolver idValueResolver = new IdValueResolver();
        idValueResolver.isInterested("id");
        idValueResolver.setValue(holdings,"test");
        assertNotNull(idValueResolver.getClass());
    }

}
