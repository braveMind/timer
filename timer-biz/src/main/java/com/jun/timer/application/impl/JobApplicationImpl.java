package com.jun.timer.application.impl;

import com.github.pagehelper.PageInfo;

import com.jun.timer.application.JobApplication;
import com.jun.timer.dao.po.JobPO;
import com.jun.timer.domain.JobDomain;
import com.jun.timer.dto.JobDto;
import com.jun.timer.dto.JobResultDto;
import com.jun.timer.dto.Page;
import com.jun.timer.utils.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xunaiyang on 2017/7/11.
 */
@Service
public class JobApplicationImpl implements JobApplication {
    @Autowired
    private JobDomain jobDomain;
    @Override
    public JobResultDto getJobList(String tenantId, Page page ,String jobOwner) {
        List<JobDto> jobDtoList = new ArrayList<>();
        List<JobPO> jobPOList = jobDomain.queryJobList(tenantId, jobOwner, page);
        PageInfo pageInfo = new PageInfo(jobPOList);
        page.setTotalCount((int)pageInfo.getTotal());
        if(CollectionUtils.isNotEmpty(jobPOList)) {
            jobDtoList = jobPOList.stream().map(p ->{
                JobDto jobDto = new JobDto();
                BeanUtils.copyProperties(p, jobDto);
                jobDto.setJobName(CommonUtils.storeName2RealName(p.getJobName()));
                return jobDto;
            }).collect(Collectors.toList());
        }
        JobResultDto jobResultDto = new JobResultDto();
        jobResultDto.setJobDtoList(jobDtoList);
        jobResultDto.setPage(page);
        return jobResultDto;
    }

    @Override
    public JobDto getJobInfo(String jobName, String appName) {
        JobPO jobPO = jobDomain.getJobInfo(jobName, appName);
        if(jobPO != null) {
            JobDto jobDto = new JobDto();
            BeanUtils.copyProperties(jobPO, jobDto);
            return jobDto;
        }
        return null;
    }

    @Override
    public void deleteJob(Integer jobId) {
        jobDomain.deleteJobById(jobId);
    }
}
