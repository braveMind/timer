package com.jun.timer.controller;



import com.jun.timer.constant.ConstantString;
import com.jun.timer.constant.RemoteUrlInfo;
import com.jun.timer.dto.JobDto;
import com.jun.timer.dto.Page;
import com.jun.timer.service.JobService;
import com.jun.timer.utils.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by xunaiyang on 2017/7/10.
 */
@RestController
@RequestMapping("/api")
public class JobController {
    private static final String tenantId= ConstantString.tenant;

    @Autowired
    private JobService jobService;

    @RequestMapping(value = "createJob", method = RequestMethod.POST)
    public HttpResult createJob(@RequestBody JobDto jobDto) {
        jobDto.setJobType(tenantId);
        return jobService.createJob(tenantId, jobDto);
    }

    @RequestMapping(value = "deleteJob/{jobId}", method = RequestMethod.POST)
    public HttpResult deleteJob(@PathVariable String jobId) {
        return jobService.deleteJob(tenantId, jobId);
    }

    @RequestMapping(value = "modifyJobTime", method = RequestMethod.POST)
    public HttpResult modifyJob(@RequestBody Map<String, String> map) {
        String jobId = map.get("jobId").trim();
        String cron = map.get("cron");
        String tenantId = map.get("tenantId");
        return jobService.modifyJobTime(tenantId, jobId, cron);
    }

    @RequestMapping(value = "modifyJob", method = RequestMethod.POST)
    public HttpResult modifyJob(@RequestBody JobDto jobDto) {
        return jobService.modifyJob(tenantId, jobDto);
    }

    @RequestMapping(value = "queryJob", method = RequestMethod.POST)
    public HttpResult queryJob(@RequestBody Page page) {

        return HttpResult.builder().data(jobService.queryJob(tenantId, page, null)).successTrue().build();
    }

    @RequestMapping(value = "pauseJob/{jobId}", method = RequestMethod.POST)
    public HttpResult pauseJob(@PathVariable String jobId) {
        return jobService.pauseJob(tenantId, jobId.trim());
    }

    @RequestMapping(value = "resumeJob/{jobId}", method = RequestMethod.POST)
    public HttpResult resumeJob(@PathVariable String jobId) {
        return jobService.resumeJob(tenantId, jobId.trim());
    }

    @RequestMapping(value = "triggerJob/{jobId}", method = RequestMethod.POST)
    public HttpResult triggerJob(@PathVariable String jobId) {
        return jobService.triggerJob(tenantId, jobId.trim());
    }
}
