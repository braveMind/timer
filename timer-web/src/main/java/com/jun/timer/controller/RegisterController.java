package com.jun.timer.controller;


import com.jun.timer.application.AppApplication;
import com.jun.timer.application.JobApplication;
import com.jun.timer.application.LogApplication;
import com.jun.timer.dto.JobDto;
import com.jun.timer.dto.LogDto;
import com.jun.timer.utils.CommonUtils;
import com.jun.timer.utils.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by xunaiyang on 2017/7/10.
 */
@RestController
@RequestMapping("/api")
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private AppApplication appApplication;


    @Autowired
    private LogApplication logApplication;

    @Autowired
    private JobApplication jobApplication;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public HttpResult register(@RequestBody Map<String, String > map) {

        String appName = map.get("appName");
        String address = map.get("address");
        appApplication.register(appName, address);
        return HttpResult.builder().data(true).successTrue().build();
    }

    @RequestMapping(value = "unregister", method = RequestMethod.POST)
    public HttpResult unregister(@RequestBody Map<String, String > map) {
        String appName = map.get("appName");
        String address = map.get("address");
        appApplication.unregister(appName, address);
        return HttpResult.builder().data(true).successTrue().build();
    }

    @RequestMapping(value = "callback", method = RequestMethod.POST)
    public HttpResult jobExecutionResult(Integer logId, Integer code) {
        if(logger.isInfoEnabled()) {
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
        if(logger.isInfoEnabled()) {
            logger.info("finish executing callback function![logId: {}]", logId);
        }
        return HttpResult.builder().data(true).successTrue().build();
    }
}
