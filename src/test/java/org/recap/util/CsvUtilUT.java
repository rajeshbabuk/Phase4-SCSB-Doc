package org.recap.util;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.recap.BaseTestCaseUT;
import org.recap.model.matchingreports.TitleExceptionReport;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CsvUtilUT extends BaseTestCaseUT {

    @InjectMocks
    CsvUtil csvUtil;

    @Test
    public void createTitleExceptionReportFile()throws Exception {
        TitleExceptionReport titleExceptionReport=new TitleExceptionReport();
        titleExceptionReport.setTitleList(Arrays.asList("test"));
        List<TitleExceptionReport> titleExceptionReports=new ArrayList<>();
        titleExceptionReports.add(titleExceptionReport);
        File file=csvUtil.createTitleExceptionReportFile("test",1,titleExceptionReports);
        assertNotNull(file);
    }
}
