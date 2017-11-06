package com.jun.timer.application;


import com.jun.timer.dto.JobDto;
import com.jun.timer.utils.HttpResult;

/**
 * Created by xunaiyang on 2017/7/5.
 */
public interface QuartzApplication {
    /**
     * 添加一个cron类型的Job（周期性任务）
     * @param jobDto
     * @return jobId
     */
    HttpResult addCronJob(String tenantId, JobDto jobDto);

    /**
     * 修改触发器调度时间
     * @param jobId
     * @param cron
     */
    HttpResult modifyJobTime(String tenantId, String jobId, String cron);

    /**
     * 修改任务基本信息
     * @param jobDto
     * @return
     */
    HttpResult modifyJob(String tenantId, JobDto jobDto);

    /**
     * 暂停指定的任务
     * @param JobId
     */
    HttpResult pauseJob(String tenantId, String JobId);

    /**
     * 恢复指定的任务
     * @param JobId
     */
    HttpResult resumeJob(String tenantId, String JobId);

    /**
     * 删除指定任务
     * @param jobId
     */
    HttpResult removeJob(String tenantId, String jobId);

    /**
     * 立即执行一次
     * @param jobId
     */
    HttpResult triggerJob(String tenantId, String jobId);
}
