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
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xunaiyang on 2017/9/11.
 */
public class RouteByRound extends BaseRouter {
    private static ConcurrentHashMap<Integer, Integer> routeCountEachJob = new ConcurrentHashMap<Integer, Integer>();
    private static long CACHE_VALID_TIME = 0;
    private static int count(int jobId) {
        // cache clear
        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            routeCountEachJob.clear();
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000*60*60*24;
        }

        // count++
        Integer count = routeCountEachJob.get(jobId);
        count = (count==null || count>1000000)?(new Random().nextInt(100)):++count;  // 初始化时主动Random一次，缓解首次压力
        routeCountEachJob.put(jobId, count);
        return count;
    }

    @Override
    public String routeStrategy(Integer jobId, List<String> addressList) {
        return addressList.get(count(jobId)%addressList.size());
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
