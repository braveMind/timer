package com.jun.timer.operate;

import com.google.gson.Gson;
import com.jun.timer.constant.RemoteUrlInfo;
import com.jun.timer.utils.HttpResult;
import com.jun.timer.utils.PropertiesUtil;
import com.jun.timer.utils.httpClient.ByteArrayEntityBuilderProvider;
import com.jun.timer.utils.httpClient.EntityBuilderProvider;
import com.jun.timer.utils.httpClient.ParamsSession;
import com.jun.timer.utils.httpClient.SessionFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jun
 * @Date 18/6/29 .
 * @des:
 */
public class RegisterServiceImp implements RegisterService {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RegisterServiceImp.class);

    @Override
    public boolean register(String address, String appName) throws IOException {
        StringBuilder sb = new StringBuilder();

        String url = sb.append(PropertiesUtil.getPropertyValueByKey(RemoteUrlInfo.SERVER_URL)).append(RemoteUrlInfo.REGISTE_URL).toString();
        return sendRequest(address, appName, url);
    }

    @Override
    public boolean unRegister(String address, String appName) throws IOException {
        StringBuilder sb = new StringBuilder();
        String url = sb.append(PropertiesUtil.getPropertyValueByKey(RemoteUrlInfo.SERVER_URL)).append(RemoteUrlInfo.UNREGISTE_URL).toString();
        return sendRequest(address, appName, url);
    }

    private boolean sendRequest(String address, String appName, String url) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("appName", appName);
        map.put("address", address);
        String json = new Gson().toJson(map);
        SessionFactory sessionFactory = new SessionFactory(new ByteArrayEntityBuilderProvider(json));
        json = sessionFactory.getRequestBodySession()
                .post(url)
                .process().getRepUtils()
                .respToString();
        HttpResult result = new Gson().fromJson(json, HttpResult.class);
        try {
            return result.getStatus() == 1 ? true : false;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean jobExecutionResult(Integer logId, Integer code) throws IOException {
        logger.info("Before send logId:[{}]", logId);
        StringBuilder sb = new StringBuilder();

        String url = sb.append(PropertiesUtil.getPropertyValueByKey(RemoteUrlInfo.SERVER_URL)).append(RemoteUrlInfo.CALLBACK_URL).toString();
        SessionFactory sessionFactory = new SessionFactory(new EntityBuilderProvider());
        ParamsSession session = (ParamsSession) sessionFactory.getParamsSession();
        Map<String, String> map = new HashMap<String, String>();
        map.put("logId", logId.toString());
        map.put("code", code.toString());
        String json = session.addParams(map).post(url)
                .process().getRepUtils().respToString();
        HttpResult result = new Gson().fromJson(json, HttpResult.class);
        logger.info("After send logId:[{} json:{}]", logId, json);
        try {
            return result.getStatus() == 1 ? true : false;
        } catch (Exception e) {
            logger.error("request sent to server failed! error message: {}", e.getMessage());
            return false;
        }
    }

}
