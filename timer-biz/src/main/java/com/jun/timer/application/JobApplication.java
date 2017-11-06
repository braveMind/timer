package com.jun.timer.application;


import com.jun.timer.dto.JobDto;
import com.jun.timer.dto.JobResultDto;
import com.jun.timer.dto.Page;

/**
 * Created by xunaiyang on 2017/7/11.
 */
public interface JobApplication {
    /**
     * 获取Job列表
     * @param page  分页信息
     * @return
     */
    JobResultDto getJobList(String tenantId, Page page, String jobOwner);

    /**
     * 根据jobName和appName查询job信息
     * @param jobName
     * @param appName
     * @return
     */
    JobDto getJobInfo(String jobName, String appName);

    /**
     * 根据jobId删除指定job
     * @param jobId
     */
    void deleteJob(Integer jobId);

}
