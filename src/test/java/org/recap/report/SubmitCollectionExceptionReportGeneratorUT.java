package org.recap.report;

import org.apache.camel.ProducerTemplate;
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
import org.recap.util.DateUtil;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 24/1/17.
 */
public class SubmitCollectionExceptionReportGeneratorUT extends BaseTestCaseUT {

    @InjectMocks
    ReportGenerator reportGenerator;

    @Mock
    ReportDetailRepository reportDetailRepository;

    @Mock
    DateUtil dateUtil;

    @InjectMocks
    FSSubmitCollectionExceptionReportGenerator FSSubmitCollectionExceptionReportGenerator;

    @InjectMocks
    S3SubmitCollectionExceptionReportGenerator s3SubmitCollectionExceptionReportGenerator;

    @InjectMocks
    S3SubmitCollectionSuccessReportGenerator S3SubmitCollectionSuccessReportGenerator;

    @InjectMocks
    FSSubmitCollectionSuccessReportGenerator FSSubmitCollectionSuccessReportGenerator;

    @InjectMocks
    S3SubmitCollectionReportGenerator s3SubmitCollectionReportGenerator;

    @Mock
    ProducerTemplate producerTemplate;

    @Test
    public void testFSSubmitCollectionExceptionReport() throws InterruptedException {
        List<ReportEntity> reportEntityList = saveSubmitCollectionExceptionReport();
        Date createdDate = reportEntityList.get(0).getCreatedDate();
        Mockito.when(reportDetailRepository.findByFileLikeAndInstitutionAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(saveSubmitCollectionExceptionReport());
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface=FSSubmitCollectionExceptionReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        String generatedReportFileName = reportGenerator.generateReport(ScsbCommonConstants.SUBMIT_COLLECTION_REPORT,"PUL", ScsbCommonConstants.SUBMIT_COLLECTION_EXCEPTION_REPORT,ScsbCommonConstants.FILE_SYSTEM,getFromDate(createdDate), getToDate(createdDate));
        assertNotNull(generatedReportFileName);
    }

    @Test
    public void testFSSubmitCollectionSuccessReportGenerator() throws InterruptedException {
        List<ReportEntity> reportEntityList = saveSubmitCollectionExceptionReport();
        Date createdDate = reportEntityList.get(0).getCreatedDate();
        Mockito.when(reportDetailRepository.findByFileLikeAndInstitutionAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(saveSubmitCollectionExceptionReport());
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface=FSSubmitCollectionSuccessReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        String generatedReportFileName = reportGenerator.generateReport(ScsbCommonConstants.SUBMIT_COLLECTION_REPORT,"PUL", ScsbCommonConstants.SUBMIT_COLLECTION_SUCCESS_REPORT,ScsbCommonConstants.FILE_SYSTEM,getFromDate(createdDate), getToDate(createdDate));
        assertNotNull(generatedReportFileName);
    }

    @Test
    public void testFTPSubmitCollectionSuccessReportGenerator() throws InterruptedException {
        List<ReportEntity> reportEntityList = saveSubmitCollectionExceptionReport();
        Date createdDate = reportEntityList.get(0).getCreatedDate();
        Mockito.when(reportDetailRepository.findByFileLikeAndInstitutionAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(saveSubmitCollectionExceptionReport());
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface= S3SubmitCollectionSuccessReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        String generatedReportFileName = reportGenerator.generateReport(ScsbCommonConstants.SUBMIT_COLLECTION_REPORT,"PUL", ScsbCommonConstants.SUBMIT_COLLECTION_SUCCESS_REPORT,ScsbCommonConstants.FTP,getFromDate(createdDate), getToDate(createdDate));
        assertNotNull(generatedReportFileName);
    }

    @Test
    public void generateReportBasedOnReportRecordNum() throws Exception {
        Mockito.when(reportDetailRepository.findByIdIn(Mockito.anyList())).thenReturn(saveSubmitCollectionExceptionReport());

        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface= s3SubmitCollectionReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        String response=reportGenerator.generateReportBasedOnReportRecordNum(Arrays.asList(1), ScsbConstants.SUBMIT_COLLECTION,ScsbCommonConstants.FTP);
        assertNotNull(response);
    }

    @Test
    public void testFTPSubmitCollectionExceptionReport() throws InterruptedException {
        List<ReportEntity> reportEntityList = saveSubmitCollectionExceptionReport();
        Date createdDate = reportEntityList.get(0).getCreatedDate();
        Mockito.when(reportDetailRepository.findByFileLikeAndInstitutionAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(saveSubmitCollectionExceptionReport());
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface= s3SubmitCollectionExceptionReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        String generatedReportFileName = reportGenerator.generateReport(ScsbCommonConstants.SUBMIT_COLLECTION_REPORT,"PUL", ScsbCommonConstants.SUBMIT_COLLECTION_EXCEPTION_REPORT,ScsbCommonConstants.FTP, getFromDate(createdDate), getToDate(createdDate));
        assertNotNull(generatedReportFileName);
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

    private List<ReportEntity> saveSubmitCollectionExceptionReport(){
        List<ReportEntity> reportEntityList = new ArrayList<>();
        List<ReportDataEntity> reportDataEntities = new ArrayList<>();
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileName(ScsbCommonConstants.SUBMIT_COLLECTION_REPORT);
        reportEntity.setType(ScsbCommonConstants.SUBMIT_COLLECTION_EXCEPTION_REPORT);
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

}