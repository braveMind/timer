package com.jun.timer.spring;


import com.jun.timer.annotation.Clock;
import com.jun.timer.annotation.Timer;
import com.jun.timer.server.NettyServer;
import com.jun.timer.server.NettyServerFactory;
import com.jun.timer.server.ServerService;
import com.jun.timer.service.RegisterService;
import com.jun.timer.utils.IPUtils;
import com.jun.timer.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by jun
 * 17/7/5 下午3:47.
 * des:
 */
public class NettySpring implements InitializingBean, DisposableBean, BeanPostProcessor {
    Logger logger = LoggerFactory.getLogger(NettySpring.class);
    private String address;
    private int port = 9100;
    private static AtomicBoolean flag = new AtomicBoolean(false);

    private ServerService serverService;
    private final String appName = PropertiesUtil.getPropertyValueByKey("app.name");



    public String getAddress() {
        if (address == null) {
            return IPUtils.getIp();
        }
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public void start() {
        ServerService serverService = new NettyServer(getAddress(), port);
        new Thread(() -> {
            serverService.start();
        }).start();
        this.serverService = serverService;
    }

    /*服务下线*/
    @Override
    public void destroy() throws Exception {
        RegisterService registerService = ApplicationContextUtil.getBean(RegisterService.class);
        registerService.unRegister(IPUtils.getIpPort(port), appName);
        serverService.stop();
        if (logger.isInfoEnabled()) logger.info("服务下线成功!");

    }

    /*服务注册*/
    @Override
    public void afterPropertiesSet() throws Exception {
        if(flag.compareAndSet(false,true)){
            RegisterService registerService = ApplicationContextUtil.getBean(RegisterService.class);;

            if (logger.isInfoEnabled()) logger.info("获取APP名字成功！[{}]", appName);
            registerService.register(IPUtils.getIpPort(port), appName);
            if (logger.isInfoEnabled()) logger.info("服务注册成功!");
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
            // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
            service.scheduleAtFixedRate(()->{
                try {
                    registerService.heartbeat(appName, IPUtils.getIpPort(port));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 10, 60, TimeUnit.SECONDS);
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            Clock clock = method.getAnnotation(Clock.class);
            if (clock != null) {
                Class clazz = bean.getClass();
                NettyServerFactory.beanMapping.put(clazz.getClass().getName(), bean);
                Timer timer = (Timer) clazz.getAnnotation(Timer.class);
                if (timer.value() != null){
                    NettyServerFactory.beanMapping.put(timer.value(), bean);
                }

            }
        }
        return bean;
    }
}
