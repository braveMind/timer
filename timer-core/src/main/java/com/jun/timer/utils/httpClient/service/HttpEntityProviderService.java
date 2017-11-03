package com.jun.timer.utils.httpClient.service;

import org.apache.http.HttpEntity;

/**
 * function:
 * Created by jun
 * 16/11/8 下午3:42.
 */
public interface HttpEntityProviderService<T> {
    T provider();
    HttpEntity builder();
}
