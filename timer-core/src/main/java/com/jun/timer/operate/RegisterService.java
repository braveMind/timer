package com.jun.timer.operate;

import java.io.IOException;

/**
 * Created by jun
 * 17/7/10 上午10:20.
 * des:移除内部rpc
 */
public interface RegisterService {
    /**
     *
     * @param address ip:port
     * @param appName
     */
    boolean register(String address, String appName) throws IOException;

    boolean unRegister(String address, String appName) throws IOException;

    Boolean jobExecutionResult(Integer logId, Integer code) throws IOException;

}
