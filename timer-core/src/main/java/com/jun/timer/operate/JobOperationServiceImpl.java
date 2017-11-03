package com.jun.timer.operate;


import com.jun.timer.dto.JobDto;
import com.jun.timer.service.JobService;
import com.jun.timer.utils.CronUtils;
import com.jun.timer.utils.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by xunaiyang on 2017/7/17.
 */
public class JobOperationServiceImpl implements JobOperationService {
    private static Logger logger = LoggerFactory.getLogger(JobOperationServiceImpl.class);

    @Autowired
    private JobService jobService;

    @Override
    public Integer addJob(String tenantId, JobDto jobDto, Date time) {
        jobDto.setCron(CronUtils.getCron(time));
        try{
            HttpResult result = jobService.createJob(tenantId, jobDto);
            int jobId = result.getStatus() == 1 ? (Integer)result.getData() : -1;
            return jobId;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return -1;
        }
    }

    @Override
    public Boolean modifyJob(String tenantId, Integer jobId, Date time) {
        try {
            HttpResult result = jobService.modifyJobTime(tenantId, jobId.toString(), CronUtils.getCron(time));
            return result.getStatus() == 1 ? true : false;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean deleteJob(String tenantId, Integer jobId) {
        try {
            HttpResult result = jobService.deleteJob(tenantId, jobId.toString());
            return result.getStatus() == 1 ? true : false;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean pauseJob(String tenantId, Integer jobId) {
        try {
            HttpResult result = jobService.pauseJob(tenantId,jobId.toString());
            return result.getStatus() == 1 ? true : false;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean resumeJob(String tenantId, Integer jobId) {
        try {
            HttpResult result = jobService.resumeJob(tenantId, jobId.toString());
            return result.getStatus() == 1 ? true : false;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean triggerJob(String tenantId, String jobId) {
        try {
            HttpResult result = jobService.triggerJob(tenantId, jobId.toString());
            return result.getStatus() == 1 ? true : false;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return false;
        }
    }
}
