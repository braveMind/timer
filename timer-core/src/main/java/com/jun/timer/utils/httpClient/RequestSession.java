package com.jun.timer.utils.httpClient;

import com.jun.timer.utils.httpClient.service.HttpEntityProviderService;
import com.jun.timer.utils.httpClient.service.SessionService;

/**
 * Created by jun
 * 17/6/29 下午4:46.
 * des:发送get请求
 */
public class RequestSession extends Session implements SessionService {

    public RequestSession(HttpEntityProviderService providerService) {
        super(providerService);

    }
    public RequestSession() {
        super(null);
    }
}
