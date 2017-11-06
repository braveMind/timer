package com.jun.timer.application.impl;

import com.jun.timer.application.QuartzApplication;
import com.jun.timer.dao.po.JobPO;
import com.jun.timer.domain.AppDomain;
import com.jun.timer.domain.JobDomain;
import com.jun.timer.dto.JobDto;
import com.jun.timer.enums.JobTypeEnum;
import com.jun.timer.enums.RouteStrategyEnum;
import com.jun.timer.job.CoreJob;
import com.jun.timer.utils.CommonUtils;
import com.jun.timer.utils.HttpResult;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xunaiyang on 2017/7/5.
 */
@Service
public class QuartzApplicationImpl implements QuartzApplication {
    private static Logger logger = LoggerFactory.getLogger(QuartzApplicationImpl.class);

    @Autowired
    private Scheduler quartzScheduler;

    @Autowired
    private JobDomain jobDomain;

    @Autowired
    private AppDomain appDomain;

    @Override
    public HttpResult addCronJob(String tenantId, JobDto jobDto) {
        if (jobDto.getRouteStrategy() == null) {
            jobDto.setRouteStrategy(RouteStrategyEnum.ROUTE_BY_FAILOVER.getType());
        }
        if (jobDto.getJobType() == null) {
            jobDto.setJobType(JobTypeEnum.ONE_TIME_JOB.getIndex());
        }
        jobDto.setStatus(Boolean.TRUE);

        List<String> addressList = appDomain.getAddressListByName(jobDto.getAppName(), Boolean.TRUE);
        if (CollectionUtils.isEmpty(addressList)) {
            return HttpResult.builder().failedFalse("创建失败！对应应用未注册或已下线！").build();
        }
        try {
            logger.info("Start creating a new job!");
            jobDto.setJobName(CommonUtils.realName2StoreName(tenantId, jobDto.getJobName()));
            JobDetail jobDetail = JobBuilder.newJob(CoreJob.class)
                    .withIdentity(jobDto.getJobName(), jobDto.getAppName()).build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobDto.getJobName(), jobDto.getAppName())
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobDto.getCron()))
                    .build();
            quartzScheduler.scheduleJob(jobDetail, trigger);
            JobPO jobPO = new JobPO();
            BeanUtils.copyProperties(jobDto, jobPO);
            jobPO.setTenantId(tenantId);
            Integer jobId = jobDomain.addJob(jobPO);
            logger.info("Finish creating a new job!");
            return HttpResult.builder().data(jobId).successTrue().build();
        } catch (SchedulerException e) {
            logger.error("An error occurred when creating a new job! error message: {}", e.getMessage());
            return HttpResult.builder().failedFalse("创建失败！" + e.getMessage()).build();
        }
    }

    @Override
    public HttpResult modifyJobTime(String tenantId, String jobId, String time) {
        JobPO jobPO = jobDomain.getJobById(Integer.parseInt(jobId));
        if (jobPO != null && !time.equals(jobPO.getCron())) {
            if (tenantId != null && tenantId.equals(jobPO.getTenantId())) {
                if (logger.isInfoEnabled()) {
                    logger.info("Start modifying the execution time! jobId:{}, old cron: {}, new cron, {}", jobId, jobPO.getCron(), time);
                }
                List<String> addressList = appDomain.getAddressListByName(jobPO.getAppName(), Boolean.TRUE);
                try {
                    TriggerKey triggerKey = TriggerKey.triggerKey(jobPO.getJobName(), jobPO.getAppName());
                    CronTrigger trigger = (CronTrigger) quartzScheduler.getTrigger(triggerKey);
                    trigger = trigger.getTriggerBuilder().withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
                    quartzScheduler.rescheduleJob(TriggerKey
                            .triggerKey(jobPO.getJobName(), jobPO.getAppName()), trigger);
                    if (!jobPO.getStatus() && CollectionUtils.isEmpty(addressList)) {
                        quartzScheduler.pauseTrigger(triggerKey);
                    }
                    jobPO.setCron(time);
                    jobDomain.updateJob(jobPO);
                    if (logger.isInfoEnabled()) {
                        logger.info("Finish modifying the execution time! jobId:{}, old cron: {}, new cron, {}", jobId, jobPO.getCron(), time);
                    }
                } catch (SchedulerException e) {
                    e.printStackTrace();
                    if (logger.isErrorEnabled()) {
                        logger.error("Failed modifying execution time! error message: {}", e.getMessage());
                    }
                    return HttpResult.builder().failedFalse("修改失败！quartz调度器发生异常").build();
                }
            } else {
                return HttpResult.builder().failedFalse("修改失败！租户信息不正确！").build();
            }
        }
        return HttpResult.builder().data(true).successTrue().build();
    }

    @Override
    public HttpResult modifyJob(String tenantId, JobDto jobDto) {
        JobPO jobPO = jobDomain.getJobById(jobDto.getId());
        if (jobPO != null) {
            if (tenantId != null && tenantId.equals(jobPO.getTenantId())) {
                if (!jobPO.getCron().equals(jobDto.getCron())) {
                    if (logger.isInfoEnabled()) {
                        logger.info("Start modifying Job info!");
                    }
                    List<String> addressList = appDomain.getAddressListByName(jobPO.getAppName(), Boolean.TRUE);
                    try {
                        TriggerKey triggerKey = TriggerKey.triggerKey(jobPO.getJobName(), jobPO.getAppName());
                        CronTrigger trigger = (CronTrigger) quartzScheduler.getTrigger(triggerKey);
                        trigger = trigger.getTriggerBuilder().withSchedule(CronScheduleBuilder.cronSchedule(jobDto.getCron())).build();
                        quartzScheduler.rescheduleJob(TriggerKey
                                .triggerKey(jobPO.getJobName(), jobPO.getAppName()), trigger);
                        if (!jobPO.getStatus() && CollectionUtils.isEmpty(addressList)) {
                            quartzScheduler.pauseTrigger(triggerKey);
                        }
                        if (logger.isInfoEnabled()) {
                            logger.info("Finish modifying job! ");
                        }
                    } catch (SchedulerException e) {
                        if (logger.isErrorEnabled()) {
                            logger.error("Failed modifying execution time! error message: {}", e.getMessage());
                        }
                        return HttpResult.builder().failedFalse("修改失败！quartz调度器发生异常").build();
                    }
                }
                jobDto.setJobName(jobPO.getJobName());
                BeanUtils.copyProperties(jobDto, jobPO);
                jobDomain.updateJob(jobPO);
            } else {
                return HttpResult.builder().failedFalse("修改失败！租户信息不正确！").build();
            }
        }
        return HttpResult.builder().data(true).successTrue().build();
    }

    @Override
    public HttpResult removeJob(String tenantId, String jobId) {
        JobPO jobPO = jobDomain.getJobById(Integer.parseInt(jobId));
        if (jobPO != null) {
            if (tenantId != null && tenantId.equals(jobPO.getTenantId())) {
                if (logger.isInfoEnabled()) {
                    logger.info("Removing job [jobId:{}]!", jobId);
                }
                try {
                    quartzScheduler.pauseTrigger(TriggerKey.triggerKey(jobPO.getJobName(),
                            jobPO.getAppName()));
                    // 移除触发器
                    quartzScheduler.unscheduleJob(TriggerKey.triggerKey(jobPO.getJobName(),
                            jobPO.getAppName()));
                    // 删除任务
                    quartzScheduler.deleteJob(JobKey.jobKey(jobPO.getJobName(),
                            jobPO.getAppName()));
                    jobDomain.deleteJobById(jobPO.getId());
                } catch (SchedulerException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("Failed removing job[jobId:{}]! error message: {}", jobId, e.getMessage());
                    }
                    return HttpResult.builder().failedFalse("删除失败！quartz调度器发生异常").build();
                }
                if (logger.isInfoEnabled()) {
                    logger.info("Job removed [jobId:{}]!", jobId);
                }
            } else {
                return HttpResult.builder().failedFalse("删除失败！租户信息不正确！").build();
            }
        }
        return HttpResult.builder().data(true).successTrue().build();
    }

    @Override
    public HttpResult pauseJob(String tenantId, String jobId) {
        JobPO jobPO = jobDomain.getJobById(Integer.parseInt(jobId));
        if (jobPO != null && jobPO.getStatus()) {
            if (tenantId != null && tenantId.equals(jobPO.getTenantId())) {
                try {
                    quartzScheduler.pauseJob(JobKey.jobKey(jobPO.getJobName(), jobPO.getAppName()));
                    jobDomain.changeStatus(jobPO.getId(), Boolean.FALSE);
                } catch (SchedulerException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("Failed pausing job[jobId:{}]! error message: {}", jobId, e.getMessage());
                    }
                    return HttpResult.builder().failedFalse("暂停失败！quartz调度器发生异常").build();
                }
            } else {
                return HttpResult.builder().failedFalse("暂停失败！租户信息不正确！").build();
            }
        }
        return HttpResult.builder().data(true).successTrue().build();
    }

    @Override
    public HttpResult resumeJob(String tenantId, String jobId) {
        JobPO jobPO = jobDomain.getJobById(Integer.parseInt(jobId));
        if (jobPO != null && !jobPO.getStatus()) {
            if (tenantId != null && tenantId.equals(jobPO.getTenantId())) {
                List<String> addressList = appDomain.getAddressListByName(jobPO.getAppName(), Boolean.TRUE);
                try {
                    if (CollectionUtils.isNotEmpty(addressList)) {
                        quartzScheduler.resumeJob(JobKey.jobKey(jobPO.getJobName(), jobPO.getAppName()));
                    }
                    jobDomain.changeStatus(jobPO.getId(), Boolean.TRUE);
                } catch (SchedulerException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("Failed resuming job[jobId:{}]! error message: {}", jobId, e.getMessage());
                    }
                    return HttpResult.builder().failedFalse("恢复失败！quartz调度器发生异常").build();
                }
            } else {
                return HttpResult.builder().failedFalse("恢复失败！租户信息不正确！").build();
            }
        }
        return HttpResult.builder().data(true).successTrue().build();
    }

    @Override
    public HttpResult triggerJob(String tenantId, String jobId) {
        JobPO jobPO = jobDomain.getJobById(Integer.parseInt(jobId));
        if (jobPO != null) {
            if (tenantId != null && tenantId.equals(jobPO.getTenantId())) {
                try {
                    quartzScheduler.triggerJob(JobKey.jobKey(jobPO.getJobName(), jobPO.getAppName()));
                } catch (SchedulerException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("Failed pausing job[jobId:{}]! error message: {}", jobId, e.getMessage());
                    }
                    return HttpResult.builder().failedFalse("单次执行失败！quartz调度器发生异常").build();
                }
            } else {
                return HttpResult.builder().failedFalse("单次执行失败！租户信息不正确！").build();
            }
        }
        return HttpResult.builder().data(true).successTrue().build();
    }
}
