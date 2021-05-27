package org.recap.util;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.recap.BaseTestCaseUT;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.model.csv.SolrExceptionReportCSVRecord;
import org.recap.model.jpa.ReportDataEntity;
import org.recap.model.jpa.ReportEntity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Created by hemalathas on 22/2/17.
 */
public class SolrExceptionCSVRecordGeneratorUT extends BaseTestCaseUT {

    @InjectMocks
    SolrExceptionCSVRecordGenerator solrExceptionCSVRecordGenerator;

    @Test
    public void testSolrExceptionRecordGenerator(){
        SolrExceptionReportCSVRecord solrExceptionReportCSVRecord = new SolrExceptionReportCSVRecord();
        solrExceptionReportCSVRecord.setDocType("Test");
        solrExceptionReportCSVRecord.setOwningInstitutionBibId("124566");
        solrExceptionReportCSVRecord.setOwningInstitution("PUL");
        solrExceptionReportCSVRecord.setBibId("123");
        solrExceptionReportCSVRecord.setHoldingsId("231");
        solrExceptionReportCSVRecord.setItemId("1");
        solrExceptionReportCSVRecord.setExceptionMessage("Title is mandatory");
        ReportEntity reportEntity = getReportEntity();
        SolrExceptionReportCSVRecord solrExceptionReportCSVRecord1 = solrExceptionCSVRecordGenerator.prepareSolrExceptionReportCSVRecord(reportEntity, solrExceptionReportCSVRecord);
        assertNotNull(solrExceptionReportCSVRecord1);
        assertEquals("PUL", solrExceptionReportCSVRecord1.getOwningInstitution());
        assertEquals(ScsbConstants.ITEM_ALREADY_ACCESSIONED, solrExceptionReportCSVRecord1.getExceptionMessage());
        Method getterMethod= solrExceptionCSVRecordGenerator.getGetterMethod("docType");
        Method setterMethod= solrExceptionCSVRecordGenerator.getSetterMethod("test");
        Method getterMethodExp= solrExceptionCSVRecordGenerator.getGetterMethod("test");
        assertNull(setterMethod);
        assertNull(getterMethodExp);
        assertNotNull(getterMethod);
    }

    private ReportEntity getReportEntity(){
        List<ReportDataEntity> reportDataEntities = new ArrayList<>();
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileName(ScsbCommonConstants.ACCESSION_REPORT);
        reportEntity.setType(ScsbCommonConstants.ACCESSION_SUMMARY_REPORT);
        reportEntity.setCreatedDate(new Date());
        reportEntity.setInstitutionName("PUL");

        ReportDataEntity successBibCountReportDataEntity = new ReportDataEntity();
        successBibCountReportDataEntity.setHeaderName("docType");
        successBibCountReportDataEntity.setHeaderValue("Test");
        reportDataEntities.add(successBibCountReportDataEntity);

        ReportDataEntity successItemCountReportDataEntity = new ReportDataEntity();
        successItemCountReportDataEntity.setHeaderName("owningInstitution");
        successItemCountReportDataEntity.setHeaderValue("PUL");
        reportDataEntities.add(successItemCountReportDataEntity);

        ReportDataEntity failedBibCountReportDataEntity = new ReportDataEntity();
        failedBibCountReportDataEntity.setHeaderName("owningInstitutionBibId");
        failedBibCountReportDataEntity.setHeaderValue("124566");
        reportDataEntities.add(failedBibCountReportDataEntity);

        ReportDataEntity existsBibCountReportDataEntity = new ReportDataEntity();
        existsBibCountReportDataEntity.setHeaderName("bibId");
        existsBibCountReportDataEntity.setHeaderValue("123");
        reportDataEntities.add(existsBibCountReportDataEntity);

        ReportDataEntity failedItemCountReportDataEntity = new ReportDataEntity();
        failedItemCountReportDataEntity.setHeaderName("holdingsId");
        failedItemCountReportDataEntity.setHeaderValue("231");
        reportDataEntities.add(failedItemCountReportDataEntity);

        ReportDataEntity reasonForBibFailureReportDataEntity = new ReportDataEntity();
        reasonForBibFailureReportDataEntity.setHeaderName("itemId");
        reasonForBibFailureReportDataEntity.setHeaderValue("1");
        reportDataEntities.add(reasonForBibFailureReportDataEntity);

        ReportDataEntity reasonForItemFailureReportDataEntity = new ReportDataEntity();
        reasonForItemFailureReportDataEntity.setHeaderName("exceptionMessage");
        reasonForItemFailureReportDataEntity.setHeaderValue(ScsbConstants.ITEM_ALREADY_ACCESSIONED);
        reportDataEntities.add(reasonForItemFailureReportDataEntity);

        reportEntity.setReportDataEntities(reportDataEntities);
        return reportEntity;

    }

}