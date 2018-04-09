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
 * @author xunaiyang
 * @date 2018/1/10
 */
public class RouteByTarget extends BaseRouter {
    private static Random localRandom = new Random();

    @Override
    public String routeStrategy(Integer jobId, List<String> addressList) {
        String targetIp = addressList.get(addressList.size() - 1);
        for(String str : addressList) {
            if(str.contains(targetIp) && !str.equals(targetIp)) {
                return str;
            }
        }
        return addressList.get(localRandom.nextInt(addressList.size()));
    }

    @Override
    public RpcResponse routeRun(JobParams jobParams, List<String> addressList, LogDto logDto) {
        addressList.add(jobParams.getTargetIp());
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
