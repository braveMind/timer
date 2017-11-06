package com.jun.timer.router.Strategy;



import com.jun.timer.common.RpcResponse;
import com.jun.timer.dto.JobParams;
import com.jun.timer.dto.LogDto;
import com.jun.timer.router.BaseRouter;
import com.jun.timer.utils.ApplicationContextHolder;
import com.jun.timer.utils.CommonUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by xunaiyang on 2017/9/11.
 */
public class RouteByConsistentHash extends BaseRouter {
    private static int VIRTUAL_NODE_NUM = 5;

    /**
     * get hash code on 2^32 ring (md5散列的方式计算hash值)
     * @param key
     * @return
     */
    private static long hash(String key) {
        // md5 byte
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes = null;
        try {
            keyBytes = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unknown string :" + key, e);
        }

        md5.update(keyBytes);
        byte[] digest = md5.digest();

        // hash code, Truncate to 32-bits
        long hashCode = ((long) (digest[3] & 0xFF) << 24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);

        long truncateHashCode = hashCode & 0xffffffffL;
        return truncateHashCode;
    }

    @Override
    public String routeStrategy(Integer jobId, List<String> addressList) {
        TreeMap<Long, String> addressRing = new TreeMap<Long, String>();
        for (String address: addressList) {
            for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                long addressHash = hash("SHARD-" + address + "-NODE-" + i);
                addressRing.put(addressHash, address);
            }
        }

        long jobHash = hash(String.valueOf(jobId));
        SortedMap<Long, String> lastRing = addressRing.tailMap(jobHash);
        if (!lastRing.isEmpty()) {
            return lastRing.get(lastRing.firstKey());
        }
        return addressRing.firstEntry().getValue();
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
