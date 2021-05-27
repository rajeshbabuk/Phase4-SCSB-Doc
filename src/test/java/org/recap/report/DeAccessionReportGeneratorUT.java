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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 25/1/17.
 */

public class DeAccessionReportGeneratorUT extends BaseTestCaseUT {

    @InjectMocks
    ReportGenerator reportGenerator;

    @Mock
    ReportDetailRepository reportDetailRepository;

    @Mock
    DateUtil dateUtil;

    @InjectMocks
    FSDeAccessionReportGenerator FSDeAccessionReportGenerator;

    @InjectMocks
    S3DeAccessionReportGenerator S3DeAccessionReportGenerator;

    @Mock
    ProducerTemplate producerTemplate;

    @Test
    public void FSDeAccessionReportGenerator() throws InterruptedException {
        List<ReportEntity> reportEntities = getReportEntity();
        Date createdDate = reportEntities.get(0).getCreatedDate();
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface=FSDeAccessionReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        Mockito.when(reportDetailRepository.findByFileAndInstitutionAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(getReportEntity());
        String generatedReportFileNameInFileSyatem = reportGenerator.generateReport(ScsbCommonConstants.DEACCESSION_REPORT, ScsbCommonConstants.PRINCETON, ScsbCommonConstants.DEACCESSION_SUMMARY_REPORT, ScsbCommonConstants.FILE_SYSTEM, dateUtil.getFromDate(createdDate), dateUtil.getToDate(createdDate));
        assertNotNull(generatedReportFileNameInFileSyatem);
    }

    @Test
    public void FTPDeAccessionReportGenerator() throws InterruptedException {
        List<ReportEntity> reportEntities = getReportEntity();
        Date createdDate = reportEntities.get(0).getCreatedDate();
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface= S3DeAccessionReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        Mockito.when(reportDetailRepository.findByFileAndInstitutionAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(getReportEntity());
        String generatedReportFileNameInFileSystem = reportGenerator.generateReport(ScsbCommonConstants.DEACCESSION_REPORT, ScsbCommonConstants.PRINCETON, ScsbCommonConstants.DEACCESSION_SUMMARY_REPORT, ScsbCommonConstants.FTP, dateUtil.getFromDate(createdDate), dateUtil.getToDate(createdDate));
        assertNotNull(generatedReportFileNameInFileSystem);
    }

    private List<ReportEntity> getReportEntity(){
        List<ReportEntity> reportEntities = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileName(ScsbCommonConstants.DEACCESSION_REPORT);
        reportEntity.setType(ScsbCommonConstants.DEACCESSION_SUMMARY_REPORT);
        reportEntity.setInstitutionName(ScsbCommonConstants.PRINCETON);
        reportEntity.setCreatedDate(new Date());

        List<ReportDataEntity> reportDataEntities = new ArrayList<>();

        ReportDataEntity dateReportDataEntity = new ReportDataEntity();
        dateReportDataEntity.setHeaderName(ScsbCommonConstants.DATE_OF_DEACCESSION);
        dateReportDataEntity.setHeaderValue(formatter.format(new Date()));
        reportDataEntities.add(dateReportDataEntity);

        ReportDataEntity owningInstitutionReportDataEntity = new ReportDataEntity();
        owningInstitutionReportDataEntity.setHeaderName(ScsbCommonConstants.OWNING_INSTITUTION);
        owningInstitutionReportDataEntity.setHeaderValue(ScsbCommonConstants.PRINCETON);
        reportDataEntities.add(owningInstitutionReportDataEntity);

        ReportDataEntity barcodeReportDataEntity = new ReportDataEntity();
        barcodeReportDataEntity.setHeaderName(ScsbCommonConstants.BARCODE);
        barcodeReportDataEntity.setHeaderValue("123");
        reportDataEntities.add(barcodeReportDataEntity);

        ReportDataEntity owningInstitutionBibIdReportDataEntity = new ReportDataEntity();
        owningInstitutionBibIdReportDataEntity.setHeaderName(ScsbCommonConstants.OWNING_INST_BIB_ID);
        owningInstitutionBibIdReportDataEntity.setHeaderValue("3456");
        reportDataEntities.add(owningInstitutionBibIdReportDataEntity);

        ReportDataEntity collectionGroupCodeReportDataEntity = new ReportDataEntity();
        collectionGroupCodeReportDataEntity.setHeaderName(ScsbCommonConstants.COLLECTION_GROUP_CODE);
        collectionGroupCodeReportDataEntity.setHeaderValue("Private");
        reportDataEntities.add(collectionGroupCodeReportDataEntity);

        ReportDataEntity statusReportDataEntity = new ReportDataEntity();
        statusReportDataEntity.setHeaderName(ScsbCommonConstants.STATUS);
        statusReportDataEntity.setHeaderValue("Success");
        reportDataEntities.add(statusReportDataEntity);

        reportEntity.setReportDataEntities(reportDataEntities);
        reportEntities.add(reportEntity);
        return reportEntities;
    }

}