
package org.recap.model.solr;
import org.junit.jupiter.api.Test;
import org.recap.BaseTestCaseUT;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Created by rajeshbabuk on 13/9/16.
 */


public class HoldingsAT extends BaseTestCaseUT {

    @Test
    public void indexHoldings() throws Exception {
        Holdings holdings = new Holdings();
        holdings.setHoldingsId(1001);
        holdings.setDocType("Holdings");
        holdings.setRoot("Holdings");
        holdings.setId("1");
        holdings.setHoldingsCreatedBy("1");
        holdings.setHoldingsCreatedDate(new Date());
        holdings.setHoldingsLastUpdatedBy("1");
        holdings.setHoldingsLastUpdatedDate(new Date());
        holdings.setDeletedHoldings(true);
        holdings.setSummaryHoldings("Test Summary Holdings Info");
        holdings.setOwningInstitution("NYPL");
        holdings.setOwningInstitutionHoldingsId("1");


        assertNotNull(holdings.getRoot());
        assertNotNull(holdings.getId());
        assertNotNull(holdings.getHoldingsCreatedBy());
        assertNotNull(holdings.getHoldingsCreatedDate());
        assertNotNull(holdings.getHoldingsLastUpdatedBy());
        assertNotNull(holdings.getHoldingsLastUpdatedDate());
        assertNotNull(holdings.isDeletedHoldings());
        assertNotNull(holdings.getOwningInstitutionHoldingsId());
        assertNotNull(holdings.getHoldingsId());
        assertNotNull(holdings.getDocType());
        assertNotNull(holdings.getSummaryHoldings());
        assertNotNull(holdings.getOwningInstitution());
    }
}

