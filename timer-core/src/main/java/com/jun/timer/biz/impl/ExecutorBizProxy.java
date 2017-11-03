package com.jun.timer.biz.impl;


import com.jun.timer.client.NettyClient;
import com.jun.timer.common.RpcRequest;
import com.jun.timer.common.RpcResponse;
import com.jun.timer.spring.ApplicationContextUtil;
import com.jun.timer.utils.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xunaiyang on 2017/7/6.
 */

public class ExecutorBizProxy {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorBizProxy.class);

    private Class<?> iface;
    private String address;
    private int port;


    public ExecutorBizProxy(Class<?> iface, String address) {
        String[] serverAddress = address.split(":");
        this.iface = iface;
        this.address = serverAddress[0];
        this.port = Integer.parseInt(serverAddress[1]);
    }

    public Object getObject() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{iface},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        RpcRequest request = new RpcRequest();
                        request.setAddress(IPUtils.getIp());
                        request.setClassName(method.getDeclaringClass().getName());
                        request.setMethodName(method.getName());
                        request.setParameterType(method.getParameterTypes());
                        request.setParameter(args);

                        logger.info("------Start Sending Requst!");
                        RpcResponse response = null;
                        try {
                            NettyClient nettyClient = ApplicationContextUtil.getBean(NettyClient.class);
                            response = nettyClient.send(request, address, port);

                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }

                        logger.info("------response received!");
                        return response;
                    }
                });
    }
}
