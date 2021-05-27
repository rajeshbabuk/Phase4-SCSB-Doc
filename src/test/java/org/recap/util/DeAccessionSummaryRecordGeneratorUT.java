package org.recap.util;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.recap.BaseTestCaseUT;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.model.csv.DeAccessionSummaryRecord;
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
public class DeAccessionSummaryRecordGeneratorUT extends BaseTestCaseUT {

    @InjectMocks
    DeAccessionSummaryRecordGenerator deAccessionSummaryRecordGenerator ;

    @Test
    public void testDeaccessionSummaryRecord(){

        ReportEntity reportEntity = getReportEntity();
        DeAccessionSummaryRecord deAccessionSummaryRecord = deAccessionSummaryRecordGenerator.prepareDeAccessionSummaryReportRecord(reportEntity);
        Method getterMethod= deAccessionSummaryRecordGenerator.getGetterMethod("Barcode");
        Method setterMethod= deAccessionSummaryRecordGenerator.getSetterMethod("test");
        Method getterMethodExp= deAccessionSummaryRecordGenerator.getGetterMethod("test");
        assertNull(setterMethod);
        assertNull(getterMethodExp);
        assertNotNull(getterMethod);
        assertNotNull(deAccessionSummaryRecord);
        assertEquals("123",deAccessionSummaryRecord.getBarcode());
        assertEquals("Shared",deAccessionSummaryRecord.getCollectionGroupCode());
        assertEquals("test",deAccessionSummaryRecord.getTitle());
        assertEquals("Available",deAccessionSummaryRecord.getStatus());
        assertEquals("PUL",deAccessionSummaryRecord.getOwningInstitution());

    }

    private ReportEntity getReportEntity(){
        List<ReportDataEntity> reportDataEntities = new ArrayList<>();
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileName(ScsbCommonConstants.DEACCESSION_REPORT);
        reportEntity.setType(ScsbCommonConstants.DEACCESSION_SUMMARY_REPORT);
        reportEntity.setCreatedDate(new Date());
        reportEntity.setInstitutionName("PUL");

        ReportDataEntity successBibCountReportDataEntity = new ReportDataEntity();
        successBibCountReportDataEntity.setHeaderName("dateOfDeAccession");
        successBibCountReportDataEntity.setHeaderValue(new Date().toString());
        reportDataEntities.add(successBibCountReportDataEntity);

        ReportDataEntity successItemCountReportDataEntity = new ReportDataEntity();
        successItemCountReportDataEntity.setHeaderName("owningInstitution");
        successItemCountReportDataEntity.setHeaderValue("PUL");
        reportDataEntities.add(successItemCountReportDataEntity);

        ReportDataEntity existsBibCountReportDataEntity = new ReportDataEntity();
        existsBibCountReportDataEntity.setHeaderName("barcode");
        existsBibCountReportDataEntity.setHeaderValue("123");
        reportDataEntities.add(existsBibCountReportDataEntity);

        ReportDataEntity failedBibCountReportDataEntity = new ReportDataEntity();
        failedBibCountReportDataEntity.setHeaderName("owningInstitutionBibId");
        failedBibCountReportDataEntity.setHeaderValue("124566");
        reportDataEntities.add(failedBibCountReportDataEntity);

        ReportDataEntity failedItemCountReportDataEntity = new ReportDataEntity();
        failedItemCountReportDataEntity.setHeaderName("title");
        failedItemCountReportDataEntity.setHeaderValue("test");
        reportDataEntities.add(failedItemCountReportDataEntity);

        ReportDataEntity reasonForBibFailureReportDataEntity = new ReportDataEntity();
        reasonForBibFailureReportDataEntity.setHeaderName("collectionGroupCode");
        reasonForBibFailureReportDataEntity.setHeaderValue("Shared");
        reportDataEntities.add(reasonForBibFailureReportDataEntity);

        ReportDataEntity status = new ReportDataEntity();
        status.setHeaderName("status");
        status.setHeaderValue("Available");
        reportDataEntities.add(status);

        ReportDataEntity reasonForItemFailureReportDataEntity = new ReportDataEntity();
        reasonForItemFailureReportDataEntity.setHeaderName("reasonForFailure");
        reasonForItemFailureReportDataEntity.setHeaderValue(ScsbConstants.ITEM_BARCDE_DOESNOT_EXIST);
        reportDataEntities.add(reasonForItemFailureReportDataEntity);

        reportEntity.setReportDataEntities(reportDataEntities);
        return reportEntity;

    }

}