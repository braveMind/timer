package com.jun.timer.utils.httpClient;

import com.jun.timer.utils.httpClient.service.HttpEntityProviderService;
import com.jun.timer.utils.httpClient.service.SessionFactoryService;

/**
 * function:
 * Created by jun
 * 16/11/8 下午4:51.
 */
public class SessionFactory implements SessionFactoryService {
    private HttpEntityProviderService providerService;
    public SessionFactory(HttpEntityProviderService httpEntityProviderService){
        this.providerService=httpEntityProviderService;
    };

     public Session getFileSession() {
        FileSession fileSession=new FileSession(providerService);
        return fileSession;
    }
    /*普通的 form请求*/
    public Session getParamsSession() {
        ParamsSession postParamsSession=new ParamsSession(providerService);
        return postParamsSession;
    }
    /*post请求 requestBody*/
    public Session getRequestBodySession() {
        RequestBodySession postParamsSession=new RequestBodySession(providerService);
        return postParamsSession;
    }
    /*发送get请求*/
    public static Session getRequestSession() {
        RequestSession postParamsSession=new RequestSession();
        return postParamsSession;
    }







}
