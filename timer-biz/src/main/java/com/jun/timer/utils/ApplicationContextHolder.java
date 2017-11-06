package com.jun.timer.utils;

import com.jun.timer.application.LogApplication;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Created by xunaiyang on 2017/7/14.
 */
@Service
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext ctx = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return (T) ctx.getBean(beanName);
    }


    public static <T> T getBean(Class<T> requiredType) {
        return (T) ctx.getBean(requiredType);
    }

    public static LogApplication getLogApplication() {
        return getBean(LogApplication.class);
    }
}
