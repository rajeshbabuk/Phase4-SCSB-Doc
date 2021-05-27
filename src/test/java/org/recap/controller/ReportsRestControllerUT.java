package org.recap.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.recap.model.reports.ReportDataRequest;
import org.recap.model.reports.ReportsRequest;
import org.recap.model.reports.ReportsResponse;
import org.recap.report.ReportGenerator;
import org.recap.util.ReportsServiceUtil;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Created by rajeshbabuk on 13/1/17.
 */
public class ReportsRestControllerUT extends BaseTestCaseUT {

    @InjectMocks
    ReportsRestController mockReportsRestController;

    @Mock
    ReportsServiceUtil reportsServiceUtil;

    @Mock
    ReportGenerator reportGenerator;

    @Test
    public void accessionDeaccessionCounts() throws Exception {
        ReportsRequest reportsRequest = new ReportsRequest();
        reportsRequest.setAccessionDeaccessionFromDate("09/27/2016");
        reportsRequest.setAccessionDeaccessionToDate("01/27/2017");
        reportsRequest.setOwningInstitutions(Arrays.asList("CUL", "PUL", "NYPL"));
        reportsRequest.setCollectionGroupDesignations(Arrays.asList("Private", "Open", "Shared"));
        Mockito.when(reportsServiceUtil.populateAccessionDeaccessionItemCounts(Mockito.any())).thenReturn(getReportsResponse("accessionDeaccessionCounts"));
        ReportsResponse incompleteRecords=mockReportsRestController.accessionDeaccessionCounts(reportsRequest);
        assertEquals("accessionDeaccessionCounts",incompleteRecords.getMessage());
    }

    @Test
    public void cgdItemCounts() throws Exception {
        ReportsRequest reportsRequest = new ReportsRequest();
        reportsRequest.setOwningInstitutions(Arrays.asList("CUL", "PUL", "NYPL"));
        reportsRequest.setCollectionGroupDesignations(Arrays.asList("Private", "Open", "Shared"));
        Mockito.when(reportsServiceUtil.populateCgdItemCounts(Mockito.any())).thenReturn(getReportsResponse("cgdItemCounts"));
        ReportsResponse incompleteRecords=mockReportsRestController.cgdItemCounts(reportsRequest);
        assertEquals("cgdItemCounts",incompleteRecords.getMessage());

    }

    @Test
    public void deaccessionResults() throws Exception {
        ReportsRequest reportsRequest = new ReportsRequest();
        reportsRequest.setAccessionDeaccessionFromDate("09/27/2016");
        reportsRequest.setAccessionDeaccessionToDate("01/27/2017");
        reportsRequest.setDeaccessionOwningInstitution("PUL");
        Mockito.when(reportsServiceUtil.populateDeaccessionResults(Mockito.any())).thenReturn(getReportsResponse("deaccessionResults"));
        ReportsResponse incompleteRecords=mockReportsRestController.deaccessionResults(reportsRequest);
        assertEquals("deaccessionResults",incompleteRecords.getMessage());
    }

    @Test
    public void testIncompleteRecords() throws Exception {
        ReportsRequest reportsRequest = new ReportsRequest();
        reportsRequest.setAccessionDeaccessionFromDate("09/27/2016");
        reportsRequest.setAccessionDeaccessionToDate("01/27/2017");
        reportsRequest.setDeaccessionOwningInstitution("PUL");
        reportsRequest.setExport(true);
        Mockito.when(reportsServiceUtil.populateIncompleteRecordsReport(Mockito.any())).thenReturn(getReportsResponse("IncompleteRecords"));
        ReportsResponse incompleteRecords=mockReportsRestController.incompleteRecords(reportsRequest);
        assertEquals("IncompleteRecords",incompleteRecords.getMessage());
    }

    @Test
    public void testGenerateCsvReports() {
        ReportDataRequest reportDataRequest = new ReportDataRequest();
        reportDataRequest.setFileName("Accession");
        reportDataRequest.setTransmissionType("FTP");
        reportDataRequest.setReportType("Summary");
        reportDataRequest.setInstitutionCode("PUL");
        Mockito.when(reportGenerator.generateReport(reportDataRequest.getFileName(),reportDataRequest.getInstitutionCode(),reportDataRequest.getReportType(), reportDataRequest.getTransmissionType(),null,null)).thenReturn("PULAccessionSummaryFTP");
        String generatedFileName=mockReportsRestController.generateCsvReports(reportDataRequest);
        assertEquals("PULAccessionSummaryFTP",generatedFileName);
    }

    @Test
    public void deaccessionResultsException() throws Exception {
        ReportsRequest reportsRequest=new ReportsRequest();
        Mockito.when(reportsServiceUtil.populateDeaccessionResults(Mockito.any())).thenThrow(NullPointerException.class);
        ReportsResponse incompleteRecords=mockReportsRestController.deaccessionResults(reportsRequest);
        assertNull(incompleteRecords.getMessage());
    }

    @Test
    public void accessionDeaccessionCountsException() throws Exception {
        ReportsRequest reportsRequest=new ReportsRequest();
        Mockito.when(reportsServiceUtil.populateAccessionDeaccessionItemCounts(Mockito.any())).thenThrow(NullPointerException.class);
        ReportsResponse incompleteRecords=mockReportsRestController.accessionDeaccessionCounts(reportsRequest);
        assertNull(incompleteRecords.getMessage());
    }

    @Test
    public void cgdItemCountsException() throws Exception {
        ReportsRequest reportsRequest=new ReportsRequest();
        Mockito.when(reportsServiceUtil.populateCgdItemCounts(Mockito.any())).thenThrow(NullPointerException.class);
        ReportsResponse incompleteRecords=mockReportsRestController.cgdItemCounts(reportsRequest);
        assertNull(incompleteRecords.getMessage());
    }

    @Test
    public void testIncompleteRecordsException() throws Exception {
        ReportsRequest reportsRequest=new ReportsRequest();
        Mockito.when(reportsServiceUtil.populateIncompleteRecordsReport(Mockito.any())).thenThrow(NullPointerException.class);
        ReportsResponse incompleteRecords=mockReportsRestController.incompleteRecords(reportsRequest);
        assertNull(incompleteRecords.getMessage());
    }

    private ReportsResponse getReportsResponse(String message) {
        ReportsResponse reportsResponse = new ReportsResponse();
        reportsResponse.setMessage(message);
        return reportsResponse;
    }
}
