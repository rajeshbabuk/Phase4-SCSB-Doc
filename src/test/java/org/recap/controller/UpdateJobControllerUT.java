package org.recap.controller;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCaseUT;
import org.recap.BaseTestCaseUT4;
import org.recap.ScsbCommonConstants;
import org.recap.ScsbConstants;
import org.recap.model.job.JobDto;
import org.recap.model.job.JobParamDto;
import org.recap.model.jpa.JobEntity;
import org.recap.model.jpa.JobParamDataEntity;
import org.recap.model.jpa.JobParamEntity;
import org.recap.repository.jpa.JobDetailsRepository;
import org.recap.repository.jpa.JobParamDetailRepository;
import org.recap.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rajeshbabuk on 20/4/17.
 */
public class UpdateJobControllerUT extends BaseTestCaseUT4 {

    @InjectMocks
    UpdateJobController updateJobController;

    @Mock
    JobDetailsRepository jobDetailsRepository;

    @Mock
    JobParamDetailRepository jobParamDetailRepository;

    @Mock
    CommonUtil commonUtil;

    @Mock
    JobParamDto jobParamDto;

    @Mock
    JobParamEntity jobParamEntity;

    @Mock
    JobEntity jobEntity;

    @Mock
    JobDto jobDto;

    @Test
    public void testUpdateJob() throws Exception {
        JobEntity jobEntity = new JobEntity();
        jobEntity.setJobName(ScsbConstants.PURGE_EXCEPTION_REQUESTS);
        String status=updateJobController.updateJob(jobEntity);
        assertEquals(ScsbCommonConstants.SUCCESS,status);
    }

    @Test
    public void testGetAllJobs() throws Exception {
        List<JobDto> jobDtoList = updateJobController.getAllJobs();
        assertNotNull(jobDtoList);
    }

    @Test
    public void testGetJobByName() throws Exception {
        Mockito.when(jobDetailsRepository.findByJobName(Mockito.anyString())).thenReturn(jobEntity);
        Mockito.when(commonUtil.convertToDto(Mockito.any(),Mockito.any())).thenReturn(jobDto);
        JobDto job = updateJobController.getJobByName(ScsbConstants.PURGE_EXCEPTION_REQUESTS);
        assertNotNull(job);
    }

    @Test
    public void testGetJobParamsByJobName() throws Exception {
        Mockito.when(commonUtil.convertToDto(Mockito.any(),Mockito.any())).thenReturn(jobParamDto);
        Mockito.when(jobParamDetailRepository.findByJobName(Mockito.anyString())).thenReturn(jobParamEntity);
        JobParamDto jobParamDto = updateJobController.getJobParamsByJobName(ScsbConstants.GENERATE_ACCESSION_REPORT_JOB);
        assertNotNull(jobParamDto);
    }
}
