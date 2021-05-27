package org.recap.report;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.model.jpa.ReportDataEntity;
import org.recap.model.jpa.ReportEntity;
import org.recap.repository.jpa.ReportDetailRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by akulak on 30/5/17.
 */
public class S3SubmitCollectionReportGeneratorUT extends BaseTestCaseUT {

    @InjectMocks
    S3SubmitCollectionReportGenerator s3SubmitCollectionReportGenerator;

    @InjectMocks
    S3SubmitCollectionSummaryReportGenerator s3SubmitCollectionSummaryReportGenerator;

    @InjectMocks
    S3SubmitCollectionFailureReportGenerator S3SubmitCollectionFailureReportGenerator;

    @InjectMocks
    S3SubmitCollectionSuccessReportGenerator S3SubmitCollectionSuccessReportGenerator;

    @InjectMocks
    FSSubmitCollectionFailureReportGenerator FSSubmitCollectionFailureReportGenerator;

    @InjectMocks
    FSSubmitCollectionSuccessReportGenerator FSSubmitCollectionSuccessReportGenerator;

    @InjectMocks
    FSSubmitCollectionSummaryReportGenerator FSSubmitCollectionSummaryReportGenerator;


    @InjectMocks
    S3SolrExceptionReportGenerator S3SolrExceptionReportGenerator;


    @InjectMocks
    CSVSolrExceptionReportGenerator CSVSolrExceptionReportGenerator;

    @Mock
    private ReportDetailRepository reportDetailRepository;

    @Mock
    ProducerTemplate producerTemplate;

    @Mock
    CamelContext camelContext;

    @InjectMocks
    ReportGenerator reportGenerator;


    @Test
    public void testFTPSubmitCollectionFailureReportGenerator() throws Exception {
        List<ReportEntity> reportEntityList =saveSubmitCollectionExceptionReport(ScsbCommonConstants.SUBMIT_COLLECTION_FAILURE_REPORT);
        Mockito.when(reportDetailRepository.findByFileLikeAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(reportEntityList);
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface= S3SubmitCollectionFailureReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        String generatedReportFileName = reportGenerator.generateReport(ScsbConstants.FTP_SUBMIT_COLLECTION_FAILURE_REPORT_Q, ScsbCommonConstants.LCCN_CRITERIA, ScsbCommonConstants.SUBMIT_COLLECTION_FAILURE_REPORT, ScsbCommonConstants.FTP,getFromDate(new Date()),getToDate(new Date()));
        assertNotNull(generatedReportFileName);
    }



    @Test
    public void testFSSubmitCollectionFailureReportGenerator() throws Exception {
        List<ReportEntity> reportEntityList=new ArrayList<>();
        ReportEntity reportEntity=new ReportEntity();
        reportEntityList.add(reportEntity);
        String generatedReportFileName = FSSubmitCollectionFailureReportGenerator.generateReport(ScsbConstants.FS_SUBMIT_COLLECTION_FAILURE_REPORT_Q,reportEntityList);
        assertTrue(FSSubmitCollectionFailureReportGenerator.isTransmitted(ScsbCommonConstants.FILE_SYSTEM));
        assertTrue(FSSubmitCollectionFailureReportGenerator.isInterested(ScsbCommonConstants.SUBMIT_COLLECTION_FAILURE_REPORT));
    }


  @Test
    public void testFTPSolrExceptionReportGenerator() throws Exception {
        List<ReportEntity> reportEntityList=new ArrayList<>();
        ReportEntity reportEntity=new ReportEntity();
        reportEntityList.add(reportEntity);
        String generatedReportFileName = S3SolrExceptionReportGenerator.generateReport(ScsbCommonConstants.FTP_SOLR_EXCEPTION_REPORT_Q,reportEntityList);
        assertTrue(S3SolrExceptionReportGenerator.isTransmitted(ScsbCommonConstants.FTP));
        assertTrue(S3SolrExceptionReportGenerator.isInterested(ScsbCommonConstants.SOLR_INDEX_EXCEPTION));
    }

    @Test
    public void testCSVSolrExceptionReportGenerator() throws Exception {
        List<ReportEntity> reportEntityList=new ArrayList<>();
        ReportEntity reportEntity=new ReportEntity();
        reportEntityList.add(reportEntity);
        String generatedReportFileName = CSVSolrExceptionReportGenerator.generateReport(ScsbCommonConstants.CSV_SOLR_EXCEPTION_REPORT_Q,reportEntityList);
        assertTrue(CSVSolrExceptionReportGenerator.isTransmitted(ScsbCommonConstants.FILE_SYSTEM));
        assertTrue(CSVSolrExceptionReportGenerator.isInterested(ScsbCommonConstants.SOLR_INDEX_EXCEPTION));
    }

    @Test
    public void FSSubmitCollectionSummaryReportGenerator() throws Exception {
        List<ReportEntity> reportEntityList=new ArrayList<>();
        ReportEntity reportEntity=new ReportEntity();
        reportEntityList.add(reportEntity);
        String generatedReportFileName = FSSubmitCollectionSummaryReportGenerator.generateReport(ScsbConstants.FS_SUBMIT_COLLECTION_SUMMARY_REPORT_Q,reportEntityList);
        assertTrue(FSSubmitCollectionSummaryReportGenerator.isTransmitted(ScsbCommonConstants.FILE_SYSTEM));
        assertTrue(FSSubmitCollectionSummaryReportGenerator.isInterested(ScsbCommonConstants.SUBMIT_COLLECTION_SUMMARY));
    }

    @Test
    public void testGenerateReport() throws Exception{
        camelContext.getEndpoint(ScsbConstants.FTP_SUBMIT_COLLECTION_REPORT_Q, MockEndpoint.class);
        boolean isInterested = s3SubmitCollectionReportGenerator.isInterested(ScsbConstants.SUBMIT_COLLECTION);
        assertTrue(isInterested);
        boolean isTransmitted = s3SubmitCollectionReportGenerator.isTransmitted(ScsbCommonConstants.FTP);
        assertTrue(isTransmitted);
        String response = s3SubmitCollectionReportGenerator.generateReport(ScsbConstants.SUBMIT_COLLECTION,saveSubmitCollectionExceptionReport(ScsbCommonConstants.SUBMIT_COLLECTION_EXCEPTION_REPORT));
        String errorResponse = s3SubmitCollectionReportGenerator.generateReport(ScsbConstants.SUBMIT_COLLECTION,null);
        assertNotNull(response);
        assertEquals(ScsbCommonConstants.SUCCESS,response);
        assertEquals(ScsbConstants.ERROR,errorResponse);
    }

    @Test
    public void testSubmitCollectionSummaryReportGenerator() throws Exception{
        camelContext.getEndpoint(ScsbConstants.FTP_SUBMIT_COLLECTION_REPORT_Q, MockEndpoint.class);
        boolean isInterested = s3SubmitCollectionSummaryReportGenerator.isInterested(ScsbCommonConstants.SUBMIT_COLLECTION_SUMMARY);
        assertTrue(isInterested);
        boolean isTransmitted = s3SubmitCollectionSummaryReportGenerator.isTransmitted(ScsbCommonConstants.FTP);
        assertTrue(isTransmitted);
        String response = s3SubmitCollectionSummaryReportGenerator.generateReport(ScsbConstants.SUBMIT_COLLECTION,saveSubmitCollectionExceptionReport(ScsbCommonConstants.SUBMIT_COLLECTION_SUMMARY));
        assertNotNull(response);
    }

    private List<ReportEntity> saveSubmitCollectionExceptionReport(String reportType){
        List<ReportEntity> reportEntityList = new ArrayList<>();
        List<ReportDataEntity> reportDataEntities = new ArrayList<>();
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileName(ScsbCommonConstants.SUBMIT_COLLECTION_REPORT);
        reportEntity.setType(reportType);
        reportEntity.setCreatedDate(new Date());
        reportEntity.setInstitutionName("PUL");

        ReportDataEntity itemBarcodeReportDataEntity = new ReportDataEntity();
        itemBarcodeReportDataEntity.setHeaderName(ScsbCommonConstants.SUBMIT_COLLECTION_ITEM_BARCODE);
        itemBarcodeReportDataEntity.setHeaderValue("123");
        reportDataEntities.add(itemBarcodeReportDataEntity);

        ReportDataEntity customerCodeReportDataEntity = new ReportDataEntity();
        customerCodeReportDataEntity.setHeaderName(ScsbCommonConstants.SUBMIT_COLLECTION_CUSTOMER_CODE);
        customerCodeReportDataEntity.setHeaderValue("PB");
        reportDataEntities.add(customerCodeReportDataEntity);

        ReportDataEntity owningInstitutionReportDataEntity = new ReportDataEntity();
        owningInstitutionReportDataEntity.setHeaderName(ScsbCommonConstants.OWNING_INSTITUTION);
        owningInstitutionReportDataEntity.setHeaderValue("1");
        reportDataEntities.add(owningInstitutionReportDataEntity);

        ReportDataEntity message = new ReportDataEntity();
        message.setHeaderName(ScsbCommonConstants.MESSAGE);
        message.setHeaderValue("1");
        reportDataEntities.add(message);

        reportEntity.setReportDataEntities(reportDataEntities);
        reportEntityList.add(reportEntity);
        return reportEntityList;
    }

    public Date getFromDate(Date createdDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(createdDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return  cal.getTime();
    }

    public Date getToDate(Date createdDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(createdDate);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
}
