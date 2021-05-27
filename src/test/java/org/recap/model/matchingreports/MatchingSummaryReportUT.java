package org.recap.model.matchingreports;

import org.junit.jupiter.api.Test;
import org.recap.BaseTestCaseUT;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 4/7/17.
 */
public class MatchingSummaryReportUT extends BaseTestCaseUT {

    @Test
    public void testMatchingSummaryReport(){
        MatchingSummaryReport matchingSummaryReport = new MatchingSummaryReport();
        matchingSummaryReport.setInstitution("PUL");
        matchingSummaryReport.setTotalBibs("12");
        matchingSummaryReport.setTotalItems("23");
        matchingSummaryReport.setSharedItemsBeforeMatching("5");
        matchingSummaryReport.setOpenItemsBeforeMatching("1");
        matchingSummaryReport.setSharedItemsAfterMatching("2");
        matchingSummaryReport.setOpenItemsAfterMatching("1");

        assertNotNull(matchingSummaryReport.getInstitution());
        assertNotNull(matchingSummaryReport.getTotalBibs());
        assertNotNull(matchingSummaryReport.getTotalItems());
        assertNotNull(matchingSummaryReport.getSharedItemsBeforeMatching());
        assertNotNull(matchingSummaryReport.getOpenItemsBeforeMatching());
        assertNotNull(matchingSummaryReport.getSharedItemsAfterMatching());
        assertNotNull(matchingSummaryReport.getOpenItemsAfterMatching());


    }

}