package com.jun.timer.operate;

import com.google.gson.Gson;
import com.jun.timer.constant.RemoteUrlInfo;
import com.jun.timer.dto.JobDto;
import com.jun.timer.utils.CronUtils;
import com.jun.timer.utils.HttpResult;
import com.jun.timer.utils.PropertiesUtil;
import com.jun.timer.utils.httpClient.ByteArrayEntityBuilderProvider;
import com.jun.timer.utils.httpClient.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xunaiyang on 2017/7/17.
 */
@Service
public class JobHttpOperationServiceImp implements JobOperationService {
    private static Logger logger = LoggerFactory.getLogger(JobHttpOperationServiceImp.class);

    @Override
    public Integer addJob(String tenantId, JobDto jobDto, Date time) {
        jobDto.setCron(CronUtils.getCron(time));
        String data = new Gson().toJson(jobDto);
        StringBuilder sb = new StringBuilder();
        String url = sb.append(PropertiesUtil.getPropertyValueByKey(RemoteUrlInfo.SERVER_URL)).append(RemoteUrlInfo.CREATE_JOB_URL).toString();
        try {
            HttpResult result = sendRequest(data, url);

            return result.getStatus() == 1 ? (Integer) result.getData() : -1;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return -1;
        }
    }

    @Override
    public Boolean modifyJob(String tenantId, Integer jobId, Date time) {
        Map<String, String> map = new HashMap<>();
        map.put("jobId", jobId.toString());
        map.put("cron", CronUtils.getCron(time));
        String data = new Gson().toJson(map);
        StringBuilder sb = new StringBuilder();
        String url = sb.append(PropertiesUtil.getPropertyValueByKey(RemoteUrlInfo.SERVER_URL)).append(RemoteUrlInfo.MODIFY_JOB_URL).toString();
        try {
            HttpResult result = sendRequest(data, url);
            return result.getStatus() == 1 ? true : false;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean deleteJob(String tenantId, Integer jobId) {
        StringBuilder sb = new StringBuilder();
        String url = sb.append(PropertiesUtil.getPropertyValueByKey(RemoteUrlInfo.SERVER_URL)).append(RemoteUrlInfo.DELETE_JOB_URL).append(jobId).toString();
        String data = "";
        try {
            HttpResult result = sendRequest(data, url);

            return result.getStatus() == 1 ? true : false;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean pauseJob(String tenantId, Integer jobId) {
        StringBuilder sb = new StringBuilder();
        String url = sb.append(PropertiesUtil.getPropertyValueByKey(RemoteUrlInfo.SERVER_URL)).append(RemoteUrlInfo.PAUSE_JOB_URL).append(jobId).toString();
        String data = "";
        try {
            HttpResult result = sendRequest(data, url);

            return result.getStatus() == 1 ? true : false;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean resumeJob(String tenantId, Integer jobId) {
        StringBuilder sb = new StringBuilder();
        String url = sb.append(PropertiesUtil.getPropertyValueByKey(RemoteUrlInfo.SERVER_URL)).append(RemoteUrlInfo.RESUME_JOB_URL).append(jobId).toString();
        String data = "";
        try {
            HttpResult result = sendRequest(data, url);

            return result.getStatus() == 1 ? true : false;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return false;
        }

    }

    @Override
    public Boolean triggerJob(String tenantId, String jobId) {
        StringBuilder sb = new StringBuilder();
        String url = sb.append(PropertiesUtil.getPropertyValueByKey(RemoteUrlInfo.SERVER_URL)).append(RemoteUrlInfo.TRIGGER_URL).append(jobId).toString();
        String data = "";
        try {
            HttpResult result = sendRequest(data, url);

            return result.getStatus() == 1 ? true : false;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return false;
        }
    }

    private HttpResult sendRequest(String data, String url) throws IOException {
        SessionFactory sessionFactory = new SessionFactory(new ByteArrayEntityBuilderProvider(data));
        data = sessionFactory.getRequestBodySession()
                .post(url)
                .process().getRepUtils()
                .respToString();
        HttpResult result = new Gson().fromJson(data, HttpResult.class);
        return result;
    }
}
