package com.jun.timer.router.Strategy;


import com.jun.timer.common.Result;
import com.jun.timer.common.RpcResponse;
import com.jun.timer.dto.JobParams;
import com.jun.timer.dto.LogDto;
import com.jun.timer.router.BaseRouter;
import com.jun.timer.utils.ApplicationContextHolder;
import com.jun.timer.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by xunaiyang on 2017/9/11.
 */
public class RouteByFailover extends BaseRouter {
    private static Logger logger = LoggerFactory.getLogger(RouteByFailover.class);

    @Override
    public String routeStrategy(Integer jobId, List<String> addressList) {
        return addressList.get(0);
    }

    @Override
    public RpcResponse routeRun(JobParams jobParams, List<String> addressList, LogDto logDto) {
        for(String address : addressList) {
            logger.info("heart beat address[{0}]", address);
            RpcResponse response = runHeartBeat(address);
            logDto.setId(null);
            logDto.setAddress(address);
            logDto.setStartTime(new Date());
            logDto.setEndTime(new Date());
            Integer logId = ApplicationContextHolder.getLogApplication().recordLogInfo(CommonUtils.getTenantId(logDto.getJobName()), logDto);
            jobParams.setLogId(logId);
            logDto.setId(logId);
            if(response != null && Result.SUCCESS == response.getResult()) {
                logger.info("heart beat success on address[{0}]", address);
                return runExcutor(jobParams, address);
            }
        }
        return null;
    }
}
