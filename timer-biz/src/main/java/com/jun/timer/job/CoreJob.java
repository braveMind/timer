package com.jun.timer.job;


import com.jun.timer.application.AppApplication;
import com.jun.timer.application.JobApplication;
import com.jun.timer.application.MessageApplication;
import com.jun.timer.common.Result;
import com.jun.timer.common.RpcResponse;
import com.jun.timer.constant.ConstantString;
import com.jun.timer.constant.RemoteUrlInfo;
import com.jun.timer.dto.EmailMessageDto;
import com.jun.timer.dto.JobDto;
import com.jun.timer.dto.JobParams;
import com.jun.timer.dto.LogDto;
import com.jun.timer.enums.JobTypeEnum;
import com.jun.timer.enums.LogTypeEnum;
import com.jun.timer.enums.RouteStrategyEnum;
import com.jun.timer.router.BaseRouter;
import com.jun.timer.router.RouteFactory;
import com.jun.timer.utils.ApplicationContextHolder;
import com.jun.timer.utils.CommonUtils;
import com.jun.timer.utils.DefaultThreadPool;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xunaiyang on 2017/7/5.
 */
public class CoreJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(CoreJob.class);

    private JobApplication jobApplication;

    private AppApplication appApplication;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        jobApplication = ApplicationContextHolder.getBean(JobApplication.class);
        appApplication = ApplicationContextHolder.getBean(AppApplication.class);
        JobKey jobKey = context.getTrigger().getJobKey();
        String jobName = jobKey.getName();
        String jobGroup = jobKey.getGroup();
        JobDto jobDto = jobApplication.getJobInfo(jobName, jobGroup);
        if (jobDto == null) {
            return;
        }
        if (JobTypeEnum.ONE_TIME_JOB.getIndex().equals(jobDto.getJobType())) {
            jobApplication.deleteJob(jobDto.getId());
        }
        JobParams jobParams = new JobParams();
        BeanUtils.copyProperties(jobDto, jobParams);
        jobParams.setJobId(jobDto.getId());

        LogDto logDto = new LogDto();
        BeanUtils.copyProperties(jobDto, logDto);
        logDto.setId(null);

        List<String> addressList = appApplication.getAddressList(jobGroup, Boolean.TRUE);
        if (CollectionUtils.isEmpty(addressList)) {
            addressList = appApplication.getAddressList(jobGroup, Boolean.FALSE);
            logDto.setLogType(LogTypeEnum.APP_OFFLINE.getIndex());
            logDto.setInfo(CommonUtils.getLogInfo(logDto));
            logDto.setStartTime(new Date());
            logDto.setEndTime(new Date());
            if (CollectionUtils.isNotEmpty(addressList)) {
                addressList.forEach(p -> {
                    logDto.setAddress(p);
                    ApplicationContextHolder.getLogApplication().recordLogInfo(CommonUtils.getTenantId(logDto.getJobName()), logDto);
                });
            } else {
                logDto.setLogType(LogTypeEnum.APP_OFFLINE.getIndex());
                ApplicationContextHolder.getLogApplication().recordLogInfo(CommonUtils.getTenantId(logDto.getJobName()), logDto);
            }
            return;
        }

        logDto.setLogType(LogTypeEnum.ADDRESS_INVALID.getIndex());
        logger.info("---------Start Executing Job!");
        RpcResponse rpcResponse = doExecute(jobParams, addressList, logDto, jobDto.getRouteStrategy(), jobDto.getRetryCount());
        logger.info("---------Finish Executing Job!");
        logDto.setEndTime(new Date());

        if (rpcResponse != null && Result.SUCCESS == rpcResponse.getResult()) {
            logDto.setLogType(LogTypeEnum.REQUEST_SEND.getIndex());

        } else if (!StringUtils.isEmpty(jobDto.getJobOwner())) {
            DefaultThreadPool.executor.submit(new AlarmTask(jobDto));
        }
        logDto.setInfo(CommonUtils.getLogInfo(logDto));
        ApplicationContextHolder.getLogApplication().modifyLogInfo(logDto);

    }

    public RpcResponse doExecute(JobParams jobParams, List<String> addressList, LogDto logDto, int routeStrategy, int retryCount) {
        retryCount = retryCount < 1 ? 1 : retryCount;
        RouteStrategyEnum strategyEnum = RouteStrategyEnum.getStrategyEnum(routeStrategy);
        strategyEnum = strategyEnum == null ? RouteStrategyEnum.ROUTE_BY_FAILOVER : strategyEnum;
        BaseRouter router = RouteFactory.getRouter(strategyEnum);

        if(RouteStrategyEnum.ROUTE_BY_FAILOVER.getType() == routeStrategy) {
            return router.routeRun(jobParams, addressList, logDto);
        } else {
            for(int i = 0; i < retryCount; i++) {
                RpcResponse rpcResponse = router.routeRun(jobParams, addressList, logDto);
                if(rpcResponse != null && Result.SUCCESS == rpcResponse.getResult()) {
                    return rpcResponse;
                }
            }
            return null;
        }
    }

    private class AlarmTask implements Runnable {
        JobDto jobDto;

        public AlarmTask(JobDto jobDto) {
            this.jobDto = jobDto;
        }

        @Override
        public void run() {
            sendElephant(this);
        }

        private void sendElephant(AlarmTask task) {
           MessageApplication messageApplication= ApplicationContextHolder.getBean(MessageApplication.class);
            if (messageApplication != null) {
                EmailMessageDto emailMessageDto = new EmailMessageDto("Timer调度系统", jobDto.getJobOwner(), jobDto.getId().longValue(),
                        MessageFormat.format(ConstantString.JOB_MESSAGE_TEMPLATE, CommonUtils.storeName2RealName(jobDto.getJobName())));

                messageApplication.sendAlarmMessage(emailMessageDto);
            }

        }
    }
}
