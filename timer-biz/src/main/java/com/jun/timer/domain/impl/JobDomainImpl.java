package com.jun.timer.domain.impl;

import com.github.pagehelper.PageHelper;

import com.jun.timer.dao.JobPOMapper;
import com.jun.timer.dao.po.JobPO;
import com.jun.timer.domain.JobDomain;
import com.jun.timer.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xunaiyang on 2017/7/10.
 */
@Component
public class JobDomainImpl implements JobDomain {
    @Autowired
    private JobPOMapper jobPOMapper;

    @Override
    public Integer addJob(JobPO jobPO) {
        jobPOMapper.insertSelective(jobPO);
        return jobPO.getId();
    }

    @Override
    public JobPO getJobById(Integer id) {
        return jobPOMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteJobById(Integer id) {
        jobPOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void changeStatus(Integer id, Boolean status) {
        jobPOMapper.updateStatus(id, status);
    }

    @Override
    public void updateJob(JobPO jobPO) {
        jobPOMapper.updateByPrimaryKeySelective(jobPO);
    }

    @Override
    public List<JobPO> queryJobList(String tenantId, String jobOwner, Page page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return jobPOMapper.selectJobList(tenantId);
    }

    @Override
    public List<String> getJobNameByOwner(String tenantId, String jobOwner) {
        return jobPOMapper.selectJobNameByJobOwner(tenantId, jobOwner);
    }

    @Override
    public JobPO getJobInfo(String jobName, String appName) {
        return jobPOMapper.selectByJobAndApp(jobName, appName);
    }

    @Override
    public List<JobPO> getJobListByAppName(String appName) {
        return jobPOMapper.selectJobListByAppName(appName);
    }
}
