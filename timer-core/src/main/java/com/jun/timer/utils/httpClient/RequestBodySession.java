package com.jun.timer.utils.httpClient;


import com.jun.timer.utils.httpClient.service.HttpEntityProviderService;
import com.jun.timer.utils.httpClient.service.SessionService;

/**
 * Created by jun
 * 17/6/8 下午3:31.
 * des:
 */
public class RequestBodySession extends Session implements SessionService {
    public RequestBodySession(HttpEntityProviderService providerService) {
        super(providerService);
    }

}
