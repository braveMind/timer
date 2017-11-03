package com.jun.timer.utils.httpClient.service;





import com.jun.timer.utils.httpClient.Session;

import java.io.IOException;

/**
 * function:统一处理 Get Post请求
 * Created by jun
 * 16/11/8 下午4:55.
 */
public interface SessionService {
    public Session process() throws IOException;

}
