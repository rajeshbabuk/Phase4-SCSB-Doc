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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by hemalathas on 17/1/17.
 */
public class AccessionReportGeneratorUT extends BaseTestCaseUT {

    @InjectMocks
    ReportGenerator reportGenerator;

    @Mock
    ReportDetailRepository reportDetailRepository;

    @Mock
    DateUtil dateUtil;

    @InjectMocks
    FSAccessionReportGenerator FSAccessionReportGenerator;

    @InjectMocks
    S3AccessionReportGenerator S3AccessionReportGenerator;

    @InjectMocks
    FSOngoingAccessionReportGenerator FSOngoingAccessionReportGenerator;

    @InjectMocks
    S3OngoingAccessionReportGenerator S3OngoingAccessionReportGenerator;

    @Mock
    ProducerTemplate producerTemplate;

    @Test
    public void testAccessionSummaryReportForFileSystem() throws Exception{
        List<ReportEntity> reportEntityList = saveSummaryReportEntity(ScsbCommonConstants.ACCESSION_SUMMARY_REPORT);
        Date createdDate = reportEntityList.get(0).getCreatedDate();
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface=FSAccessionReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        Mockito.when(reportDetailRepository.findByFileAndInstitutionAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(saveSummaryReportEntity(ScsbCommonConstants.ACCESSION_SUMMARY_REPORT));
        String generatedReportFileName = reportGenerator.generateReport(ScsbCommonConstants.ACCESSION_REPORT, ScsbCommonConstants.PRINCETON, ScsbCommonConstants.ACCESSION_SUMMARY_REPORT, ScsbCommonConstants.FILE_SYSTEM, dateUtil.getFromDate(createdDate), dateUtil.getToDate(createdDate));
        assertNotNull(generatedReportFileName);
    }

    @Test
    public void testAccessionSummaryReportForFTP() throws Exception{
        List<ReportEntity> reportEntityList = saveSummaryReportEntity(ScsbCommonConstants.ACCESSION_SUMMARY_REPORT);
        Date createdDate = reportEntityList.get(0).getCreatedDate();
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface= S3AccessionReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        Mockito.when(reportDetailRepository.findByFileAndInstitutionAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(saveSummaryReportEntity(ScsbCommonConstants.ACCESSION_SUMMARY_REPORT));
        String generatedReportFileName = reportGenerator.generateReport(ScsbCommonConstants.ACCESSION_REPORT, ScsbCommonConstants.PRINCETON, ScsbCommonConstants.ACCESSION_SUMMARY_REPORT, ScsbCommonConstants.FTP, dateUtil.getFromDate(createdDate), dateUtil.getToDate(createdDate));
        assertNotNull(generatedReportFileName);
    }

    @Test
    public void testOngoingAccessionSummaryReportForFileSystem() throws Exception{
        List<ReportEntity> reportEntityList = saveSummaryReportEntity(ScsbConstants.ONGOING_ACCESSION_REPORT);
        Date createdDate = reportEntityList.get(0).getCreatedDate();
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface=FSOngoingAccessionReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        Mockito.when(reportDetailRepository.findByFileAndInstitutionAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(saveSummaryReportEntity(ScsbConstants.ONGOING_ACCESSION_REPORT));
        String generatedReportFileName = reportGenerator.generateReport(ScsbCommonConstants.ACCESSION_REPORT, ScsbCommonConstants.PRINCETON, ScsbConstants.ONGOING_ACCESSION_REPORT, ScsbCommonConstants.FILE_SYSTEM, dateUtil.getFromDate(createdDate), dateUtil.getToDate(createdDate));
        assertNotNull(generatedReportFileName);
    }

    @Test
    public void testOngoingAccessionSummaryReportForFTP() throws Exception{
        List<ReportEntity> reportEntityList = saveSummaryReportEntity(ScsbConstants.ONGOING_ACCESSION_REPORT);
        Date createdDate = reportEntityList.get(0).getCreatedDate();
        List<ReportGeneratorInterface> reportGenerators=new ArrayList<>();
        ReportGeneratorInterface reportGeneratorInterface= S3OngoingAccessionReportGenerator;
        reportGenerators.add(reportGeneratorInterface);
        ReflectionTestUtils.setField(reportGenerator,"reportGenerators",reportGenerators);
        Mockito.when(reportDetailRepository.findByFileAndInstitutionAndTypeAndDateRange(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(saveSummaryReportEntity(ScsbConstants.ONGOING_ACCESSION_REPORT));
        String generatedReportFileName = reportGenerator.generateReport(ScsbCommonConstants.ACCESSION_REPORT, ScsbCommonConstants.PRINCETON, ScsbConstants.ONGOING_ACCESSION_REPORT, ScsbCommonConstants.FTP, dateUtil.getFromDate(createdDate), dateUtil.getToDate(createdDate));
        assertNotNull(generatedReportFileName);
    }


    private List<ReportEntity> saveSummaryReportEntity(String reportType){
        List<ReportEntity> reportEntityList = new ArrayList<>();
        List<ReportDataEntity> reportDataEntities = new ArrayList<>();
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileName(ScsbCommonConstants.ACCESSION_REPORT);
        reportEntity.setType(reportType);
        reportEntity.setCreatedDate(new Date());
        reportEntity.setInstitutionName("PUL");

        ReportDataEntity successBibCountReportDataEntity = new ReportDataEntity();
        successBibCountReportDataEntity.setHeaderName(ScsbCommonConstants.BIB_SUCCESS_COUNT);
        successBibCountReportDataEntity.setHeaderValue(String.valueOf(1));
        reportDataEntities.add(successBibCountReportDataEntity);

        ReportDataEntity successItemCountReportDataEntity = new ReportDataEntity();
        successItemCountReportDataEntity.setHeaderName(ScsbCommonConstants.ITEM_SUCCESS_COUNT);
        successItemCountReportDataEntity.setHeaderValue(String.valueOf(1));
        reportDataEntities.add(successItemCountReportDataEntity);

        ReportDataEntity existsBibCountReportDataEntity = new ReportDataEntity();
        existsBibCountReportDataEntity.setHeaderName(ScsbCommonConstants.NUMBER_OF_BIB_MATCHES);
        existsBibCountReportDataEntity.setHeaderValue(String.valueOf(0));
        reportDataEntities.add(existsBibCountReportDataEntity);

        ReportDataEntity failedBibCountReportDataEntity = new ReportDataEntity();
        failedBibCountReportDataEntity.setHeaderName(ScsbCommonConstants.BIB_FAILURE_COUNT);
        failedBibCountReportDataEntity.setHeaderValue(String.valueOf(0));
        reportDataEntities.add(failedBibCountReportDataEntity);

        ReportDataEntity failedItemCountReportDataEntity = new ReportDataEntity();
        failedItemCountReportDataEntity.setHeaderName(ScsbCommonConstants.ITEM_FAILURE_COUNT);
        failedItemCountReportDataEntity.setHeaderValue(String.valueOf(0));
        reportDataEntities.add(failedItemCountReportDataEntity);

        ReportDataEntity reasonForBibFailureReportDataEntity = new ReportDataEntity();
        reasonForBibFailureReportDataEntity.setHeaderName(ScsbConstants.FAILURE_BIB_REASON);
        reasonForBibFailureReportDataEntity.setHeaderValue("");
        reportDataEntities.add(reasonForBibFailureReportDataEntity);

        ReportDataEntity reasonForItemFailureReportDataEntity = new ReportDataEntity();
        reasonForItemFailureReportDataEntity.setHeaderName(ScsbConstants.FAILURE_ITEM_REASON);
        reasonForItemFailureReportDataEntity.setHeaderValue("");
        reportDataEntities.add(reasonForItemFailureReportDataEntity);

        reportEntity.setReportDataEntities(reportDataEntities);
        reportEntityList.add(reportEntity);
        return reportEntityList;

    }

}