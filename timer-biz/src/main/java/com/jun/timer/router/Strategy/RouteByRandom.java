package com.jun.timer.router.Strategy;


import com.jun.timer.common.RpcResponse;
import com.jun.timer.dto.JobParams;
import com.jun.timer.dto.LogDto;
import com.jun.timer.router.BaseRouter;
import com.jun.timer.utils.ApplicationContextHolder;
import com.jun.timer.utils.CommonUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by xunaiyang on 2017/9/11.
 */
public class RouteByRandom extends BaseRouter {
    private static Random localRandom = new Random();

    @Override
    public String routeStrategy(Integer jobId, List<String> addressList) {
        return addressList.get(localRandom.nextInt(addressList.size()));
    }

    @Override
    public RpcResponse routeRun(JobParams jobParams, List<String> addressList, LogDto logDto) {
        String address = routeStrategy(jobParams.getJobId(), addressList);
        logDto.setId(null);
        logDto.setAddress(address);
        logDto.setStartTime(new Date());
        logDto.setEndTime(new Date());
        Integer logId = ApplicationContextHolder.getLogApplication().recordLogInfo(CommonUtils.getTenantId(logDto.getJobName()), logDto);
        jobParams.setLogId(logId);
        logDto.setId(logId);
        return runExcutor(jobParams, address);
    }
}
