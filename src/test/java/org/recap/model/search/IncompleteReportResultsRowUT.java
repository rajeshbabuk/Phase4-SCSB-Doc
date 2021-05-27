package org.recap.model.search;

import org.junit.jupiter.api.Test;
import org.recap.BaseTestCaseUT;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 3/7/17.
 */
public class IncompleteReportResultsRowUT extends BaseTestCaseUT {

    @Test
    public void testIncompleteReportResultsRow(){
        IncompleteReportResultsRow incompleteReportResultsRow = new IncompleteReportResultsRow();
        incompleteReportResultsRow.setBarcode("335454489747856875");
        incompleteReportResultsRow.setOwningInstitution("PUL");
        incompleteReportResultsRow.setAuthor("John");
        incompleteReportResultsRow.setCreatedDate(new Date().toString());
        incompleteReportResultsRow.setCustomerCode("AD");
        incompleteReportResultsRow.setTitle("test");
        assertNotNull(incompleteReportResultsRow.getAuthor());
        assertNotNull(incompleteReportResultsRow.getBarcode());
        assertNotNull(incompleteReportResultsRow.getCreatedDate());
        assertNotNull(incompleteReportResultsRow.getCustomerCode());
        assertNotNull(incompleteReportResultsRow.getOwningInstitution());
        assertNotNull(incompleteReportResultsRow.getTitle());
    }

}