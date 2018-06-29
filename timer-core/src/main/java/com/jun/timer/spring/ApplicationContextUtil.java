package com.jun.timer.spring;

import com.jun.timer.operate.RegisterService;
import com.jun.timer.operate.RegisterServiceImp;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by jun
 * 17/7/23 下午1:09.
 * des:
 */
public class ApplicationContextUtil implements ApplicationContextAware{

    private static ApplicationContext ctx = null;
    private  static final RegisterService register=new RegisterServiceImp();

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
    public static RegisterService getRegister(){
        return register;
    }

}
