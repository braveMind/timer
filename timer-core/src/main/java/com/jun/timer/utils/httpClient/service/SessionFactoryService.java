package com.jun.timer.utils.httpClient.service;


import com.jun.timer.utils.httpClient.Session;

/**
 * function:针对Post请求进行封装
 * Created by jun
 * 16/11/8 下午4:12.
 */
public interface SessionFactoryService {
    Session getFileSession();
    Session getParamsSession();
}
