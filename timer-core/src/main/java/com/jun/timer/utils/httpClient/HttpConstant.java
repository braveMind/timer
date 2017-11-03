package com.jun.timer.utils.httpClient;

/**
 * Created by jun on 16/7/2.
 */
public class HttpConstant {
    /*
    * 最大连接数
    * */
    public final static int DEFAULT_PORT = 80;
    public final static int PER_ROTER_NUMBER = 80;


    public final static int MAX_TOTAL_CONNECTIONS = 800;
    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = 60000;
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 400;
    /**
     * 连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 10000;
    /**
     * 读取超时时间
     */
    public final static int READ_TIMEOUT = 10000;
}
