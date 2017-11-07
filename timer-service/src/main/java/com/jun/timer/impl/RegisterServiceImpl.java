package com.jun.timer.impl;


import com.jun.timer.application.AppApplication;
import com.jun.timer.application.JobApplication;
import com.jun.timer.application.LogApplication;
import com.jun.timer.dto.JobDto;
import com.jun.timer.dto.LogDto;
import com.jun.timer.service.RegisterService;
import com.jun.timer.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * Created by xunaiyang on 2017/9/20.
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private AppApplication appApplication;


    @Autowired
    private LogApplication logApplication;

    @Autowired
    private JobApplication jobApplication;

    @Override
    public boolean register(String address, String appName) throws IOException {
        appApplication.register(appName, address);
        return true;
    }

    @Override
    public boolean unRegister(String address, String appName) throws IOException {
        appApplication.unregister(appName, address);
        return true;
    }

    @Override
    public Boolean jobExecutionResult(Integer logId, Integer code) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("start executing callback function![logId: {}]", logId);
        }

        LogDto logDto = logApplication.getLogInfoById(logId);
        JobDto jobDto = jobApplication.getJobInfo(logDto.getJobName(), logDto.getAppName());
        logDto.setClassName(jobDto.getClassName());
        logDto.setMethodName(jobDto.getMethodName());
        logDto.setParams(jobDto.getParams());

        logDto.setLogType(code.toString());
        logDto.setEndTime(new Date());
        logDto.setInfo(CommonUtils.getLogInfo(logDto));
        logApplication.modifyLogInfo(logDto);
        if (logger.isInfoEnabled()) {
            logger.info("finish executing callback function![logId: {}]", logId);
        }
        return true;
    }

    @Override
    public boolean heartbeat(String appName, String address) throws IOException {
        appApplication.changeActiveTime(appName, address);
        return true;
    }
}
