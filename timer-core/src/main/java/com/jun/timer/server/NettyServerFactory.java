package com.jun.timer.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xunaiyang on 2017/7/6.
 */
public class NettyServerFactory {

    public static Map<String, Object> beanMapping = new HashMap<String, Object>();

    public static void putService(Class<?> iface, Object serviceBean){
        beanMapping.put(iface.getName(), serviceBean);
    }
}
