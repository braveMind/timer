package com.jun.timer.domain;


import com.jun.timer.dao.po.JobPO;
import com.jun.timer.dto.Page;

import java.util.List;

/**
 * Created by xunaiyang on 2017/7/10.
 */
public interface JobDomain {
    /**
     * 新建job
     * @param jobPO
     * @return jobId
     */
    Integer addJob(JobPO jobPO);

    /**
     * 查询Job信息
     * @param id
     * @return
     */
    JobPO getJobById(Integer id);

    /**
     * 删除Job
     * @param id jobId
     */
    void deleteJobById(Integer id);

    /**
     * 更改Job状态
     * @param id
     * @param status TRUE=启用  FALSE=暂停
     */
    void changeStatus(Integer id, Boolean status);

    void updateJob(JobPO jobPO);

    List<JobPO> queryJobList(String tenantId, String jobOwner, Page page);

    List<String> getJobNameByOwner(String tenantId, String jobOwner);

    JobPO getJobInfo(String jobName, String appName);

    /**
     * 根据应用名获取已暂停的任务列表
     * @param appName
     * @return
     */
    List<JobPO> getJobListByAppName(String appName);

}
