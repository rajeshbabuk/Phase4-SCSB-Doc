package org.recap.util;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.recap.BaseTestCaseUT;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.model.csv.OngoingAccessionReportRecord;
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
public class OngoingAccessionReportGeneratorUT extends BaseTestCaseUT {

    @InjectMocks
    OngoingAccessionReportGenerator ongoingAccessionReportGenerator;


    @Test
    public void testOngoingAccessionReportGeneration(){
        ReportEntity reportEntity = getReportEntity();
        OngoingAccessionReportRecord ongoingAccessionReportRecord = ongoingAccessionReportGenerator.prepareOngoingAccessionReportRecord(reportEntity);
        assertNotNull(ongoingAccessionReportRecord);
        assertEquals("PB",ongoingAccessionReportRecord.getCustomerCode());
        assertEquals("123",ongoingAccessionReportRecord.getItemBarcode());
        assertEquals("Test",ongoingAccessionReportRecord.getMessage());
        Method getterMethodExp= ongoingAccessionReportGenerator.getGetterMethod("Barcode");
        Method getterMethod= ongoingAccessionReportGenerator.getGetterMethod("itemBarcode");
        Method setterMethodExp= ongoingAccessionReportGenerator.getSetterMethod("Barcode");
        assertNull(getterMethodExp);
        assertNull(setterMethodExp);
        assertNotNull(getterMethod);
    }



    private ReportEntity getReportEntity(){
        List<ReportDataEntity> reportDataEntities = new ArrayList<>();
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setFileName(ScsbCommonConstants.ACCESSION_REPORT);
        reportEntity.setType(ScsbConstants.ONGOING_ACCESSION_REPORT);
        reportEntity.setCreatedDate(new Date());
        reportEntity.setInstitutionName("PUL");

        ReportDataEntity successBibCountReportDataEntity = new ReportDataEntity();
        successBibCountReportDataEntity.setHeaderName("customerCode");
        successBibCountReportDataEntity.setHeaderValue("PB");
        reportDataEntities.add(successBibCountReportDataEntity);

        ReportDataEntity existsBibCountReportDataEntity = new ReportDataEntity();
        existsBibCountReportDataEntity.setHeaderName("itemBarcode");
        existsBibCountReportDataEntity.setHeaderValue("123");
        reportDataEntities.add(existsBibCountReportDataEntity);

        ReportDataEntity failedBibCountReportDataEntity = new ReportDataEntity();
        failedBibCountReportDataEntity.setHeaderName("message");
        failedBibCountReportDataEntity.setHeaderValue("Test");
        reportDataEntities.add(failedBibCountReportDataEntity);

        reportEntity.setReportDataEntities(reportDataEntities);
        return reportEntity;

    }


}