package org.recap.model.matchingreports;

import org.junit.jupiter.api.Test;
import org.recap.BaseTestCaseUT;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 4/7/17.
 */
public class MatchingSerialAndMVMReportsUT extends BaseTestCaseUT {

    @Test
    public void testMatchingSerialAndMVMReports(){
        MatchingSerialAndMVMReports matchingSerialAndMVMReports = new MatchingSerialAndMVMReports();
        matchingSerialAndMVMReports.setOwningInstitutionId("1");
        matchingSerialAndMVMReports.setTitle("MatchingSerialAndMVMReports");
        matchingSerialAndMVMReports.setSummaryHoldings("Test");
        matchingSerialAndMVMReports.setVolumePartYear("test");
        matchingSerialAndMVMReports.setUseRestriction("No Restriction");
        matchingSerialAndMVMReports.setBibId("15255");
        matchingSerialAndMVMReports.setOwningInstitutionBibId("45454564");
        matchingSerialAndMVMReports.setBarcode("33145454558247856");
        assertNotNull(matchingSerialAndMVMReports.getOwningInstitutionId());
        assertNotNull(matchingSerialAndMVMReports.getTitle());
        assertNotNull(matchingSerialAndMVMReports.getSummaryHoldings());
        assertNotNull(matchingSerialAndMVMReports.getVolumePartYear());
        assertNotNull(matchingSerialAndMVMReports.getUseRestriction());
        assertNotNull(matchingSerialAndMVMReports.getBibId());
        assertNotNull(matchingSerialAndMVMReports.getOwningInstitutionBibId());
        assertNotNull(matchingSerialAndMVMReports.getBarcode());
    }

}