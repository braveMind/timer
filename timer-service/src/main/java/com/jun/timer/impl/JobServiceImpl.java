package com.jun.timer.impl;

;
import com.jun.timer.application.JobApplication;
import com.jun.timer.application.QuartzApplication;
import com.jun.timer.dto.JobDto;
import com.jun.timer.dto.Page;
import com.jun.timer.service.JobService;
import com.jun.timer.utils.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xunaiyang on 2017/9/20.
 */
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private QuartzApplication quartzApplication;

    @Autowired
    private JobApplication jobApplication;

    @Override
    public HttpResult createJob(String tenantId, JobDto jobDto) {
        return quartzApplication.addCronJob(tenantId, jobDto);
    }

    @Override
    public HttpResult deleteJob(String tenantId, String jobId) {
        return quartzApplication.removeJob(tenantId, jobId);
    }

    @Override
    public HttpResult modifyJobTime(String tenantId, String jobId, String cron) {
        return quartzApplication.modifyJobTime(tenantId, jobId, cron);
    }

    @Override
    public HttpResult modifyJob(String tenantId, JobDto jobDto) {
        return quartzApplication.modifyJob(tenantId, jobDto);
    }

    @Override
    public HttpResult queryJob(String tenantId, Page page, String jobOwner) {
        return HttpResult.builder().data(jobApplication.getJobList(tenantId, page, jobOwner)).successTrue().build();
    }

    @Override
    public HttpResult pauseJob(String tenantId, String jobId) {
        return quartzApplication.pauseJob(tenantId, jobId);
    }

    @Override
    public HttpResult resumeJob(String tenantId, String jobId) {
        return quartzApplication.resumeJob(tenantId, jobId.trim());
    }

    @Override
    public HttpResult triggerJob(String tenantId, String jobId) {
        return quartzApplication.triggerJob(tenantId, jobId.trim());
    }
}
