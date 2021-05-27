package org.recap.model.reports;

import org.junit.jupiter.api.Test;
import org.recap.BaseTestCaseUT;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 3/7/17.
 */
public class ReportDataRequestUT extends BaseTestCaseUT {

    @Test
    public void testReportDataRequest(){
        ReportDataRequest reportDataRequest = new ReportDataRequest();
        reportDataRequest.setFileName("Accession_Summary_Report");
        reportDataRequest.setInstitutionCode("PUL");
        reportDataRequest.setReportType("Summary");
        reportDataRequest.setTransmissionType("FTP");
        assertNotNull(reportDataRequest.getFileName());
        assertNotNull(reportDataRequest.getInstitutionCode());
        assertNotNull(reportDataRequest.getReportType());
        assertNotNull(reportDataRequest.getTransmissionType());
    }

}