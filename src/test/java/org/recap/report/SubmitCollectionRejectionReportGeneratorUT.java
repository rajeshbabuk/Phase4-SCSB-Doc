package org.recap.report;

import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.recap.ScsbCommonConstants;
import org.recap.model.jpa.ReportDataEntity;
import org.recap.model.jpa.ReportEntity;
import org.recap.repository.jpa.ReportDetailRepository;
import org.recap.util.DateUtil;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 24/1/17.
 */
public class SubmitCollectionRejectionReportGeneratorUT extends BaseTestCaseUT {

    @InjectMocks
    ReportGenerator reportGenerator;

    @Mock
    ReportDetailRepository reportDetailRepository;

    @Mock
    DateUtil dateUtil;

    @Mock
    ProducerTemplate producerTemplate;

    @InjectMocks
    FSSubmitCollectionRejectionReportGenerator FSSubmitCollectionRejectionReportGenerator;

    @InjectMocks
    S3SubmitCollectionRejectionReportGenerator S3SubmitCollectionRejectionReportGenerator;

    @Test
    public void testFSSubmitCollectionRejectionReport() throws InterruptedException {
        List<ReportEntity> reportEntityList = saveSubmitCollectionRejectionReport();
        Date createdDate = reportEntityList.get(0).getCreatedDate();
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface=FSSubmitCollectionRejectionReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        Mockito.when(reportDetailRepository.findByFileLikeAndInstitutionAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(saveSubmitCollectionRejectionReport());
        String generatedReportFileName = reportGenerator.generateReport(ScsbCommonConstants.SUBMIT_COLLECTION_REPORT,"PUL", ScsbCommonConstants.SUBMIT_COLLECTION_REJECTION_REPORT,ScsbCommonConstants.FILE_SYSTEM, dateUtil.getFromDate(createdDate), dateUtil.getToDate(createdDate));
        assertNotNull(generatedReportFileName);
    }

    @Test
    public void testFTPSubmitCollectionRejectionReport() throws InterruptedException {
        List<ReportEntity> reportEntityList = saveSubmitCollectionRejectionReport();
        Date createdDate = reportEntityList.get(0).getCreatedDate();
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface= S3SubmitCollectionRejectionReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        Mockito.when(reportDetailRepository.findByFileLikeAndInstitutionAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(saveSubmitCollectionRejectionReport());
        String generatedReportFileName = reportGenerator.generateReport(ScsbCommonConstants.SUBMIT_COLLECTION_REPORT,"PUL", ScsbCommonConstants.SUBMIT_COLLECTION_REJECTION_REPORT,ScsbCommonConstants.FTP, dateUtil.getFromDate(createdDate), dateUtil.getToDate(createdDate));
        assertNotNull(generatedReportFileName);
    }

    private List<ReportEntity> saveSubmitCollectionRejectionReport(){
        List<ReportEntity> reportEntityList = new ArrayList<>();
        List<ReportDataEntity> reportDataEntities = new ArrayList<>();
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileName(ScsbCommonConstants.SUBMIT_COLLECTION_REPORT);
        reportEntity.setType(ScsbCommonConstants.SUBMIT_COLLECTION_REJECTION_REPORT);
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

        reportEntity.setReportDataEntities(reportDataEntities);
        reportEntityList.add(reportEntity);
        return reportEntityList;
    }

}