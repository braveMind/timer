package com.jun.timer.biz.impl;


import com.jun.timer.annotation.Clock;
import com.jun.timer.biz.ExecutorBiz;
import com.jun.timer.common.Result;
import com.jun.timer.common.RpcResponse;
import com.jun.timer.dto.JobParams;
import com.jun.timer.server.NettyServerFactory;
import com.jun.timer.service.RegisterService;
import com.jun.timer.spring.ApplicationContextUtil;
import com.jun.timer.utils.AopTargetUtils;
import org.slf4j.Logger;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xunaiyang on 2017/7/6.
 */
public class ExecutorBizImpl implements ExecutorBiz, Closeable {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ExecutorBizImpl.class);
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ConcurrentHashMap<String, Class> classMap = new ConcurrentHashMap<String, Class>();
    private final ConcurrentHashMap<String, Method> methodMap = new ConcurrentHashMap<String, Method>();

    @Override
    public RpcResponse run(JobParams jobParams) {
        executorService.execute(new Executor(jobParams, ApplicationContextUtil.getBean(RegisterService.class)));
        return new RpcResponse(Result.SUCCESS, "客户端RPC调用成功！");
    }

    @Override
    public RpcResponse heartBeat() {
        return new RpcResponse(Result.SUCCESS, "Heart beat success");
    }

    @Override
    public void close() throws IOException {
        executorService.shutdown();
    }

    class Executor implements Runnable {
        private JobParams jobParams;
        private RegisterService registerService;

        public Executor(JobParams jobParams, RegisterService registerService) {
            this.jobParams = jobParams;
            this.registerService = registerService;
        }

        @Override
        public void run() {
            if (log.isInfoEnabled()) {
                log.info("业务方法开始执行！");
            }
            String clazzName = jobParams.getClassName();
            Object jobCallBack = NettyServerFactory.beanMapping.get(clazzName);
            if (AopUtils.isAopProxy(jobCallBack)) {
                try {

                    Object jobTarget = AopTargetUtils.getTarget(jobCallBack);
                    Method[] methods = jobTarget.getClass().getMethods();
                    for (Method method : methods) {
                        Clock clock = method.getAnnotation(Clock.class);
                        if (clock != null) {
                            if (!StringUtils.isEmpty(clock.name())) {
                                methodMap.put(clock.name(), method);
                            } else {
                                methodMap.put(method.getName(), method);
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (!classMap.containsKey(jobParams.getClassName())) {
                    classMap.put(clazzName, jobCallBack.getClass());
                }
                if (jobCallBack == null) {
                    try {
                        jobCallBack = Class.forName(jobParams.getClassName()).newInstance();
                    } catch (InstantiationException e) {
                        if (log.isErrorEnabled()) {
                            log.error(e.getMessage());
                        }
                    } catch (IllegalAccessException e) {
                        if (log.isErrorEnabled()) {
                            log.error(e.getMessage());
                        }
                    } catch (ClassNotFoundException e) {
                        if (log.isErrorEnabled()) {
                            log.error(e.getMessage());
                        }
                    }
                }
            /*目前参数支持String类型，Object序列化*/
                Method[] methods = classMap.get(clazzName).getMethods();
                //aop 方法
                for (Method method : methods) {
                    Clock clock = method.getAnnotation(Clock.class);
                    if (clock != null) {
                        if (!StringUtils.isEmpty(clock.name())) {

                            methodMap.put(clock.name(), method);
                        } else {
                            methodMap.put(method.getName(), method);
                        }
                    }
                }

            }

            executeJob(jobCallBack);

        }


        private void executeJob(Object jobCallBack) {
           // Transaction t = Cat.newTransaction("business call", "start business");
            Method m = methodMap.get(jobParams.getMethodName());
            Assert.notNull(m, "business method not found");
            Clock clock = m.getAnnotation(Clock.class);
            try {
                if (m != null && m.getParameterTypes().length == 1) {
                    m.invoke(jobCallBack, (String) jobParams.getParams());
                    log.info("business ok");
                } else if (m != null && m.getParameterTypes().length == 0) {
                    m.invoke(jobCallBack);
                    log.info("business ok");
                }

                /*job执行完毕后进行 客户端 success 方法执行*/
                registerService.jobExecutionResult(jobParams.getLogId(), Result.EXECUTE_SUCCESS.getCode());
                if (!StringUtils.isEmpty(clock.success())) {
                    Method success = jobCallBack.getClass().getMethod(clock.success());
                    if (success != null) {
                        success.invoke(jobCallBack);
                    }
                }

             //   t.setStatus(Transaction.SUCCESS);
            } catch (Exception e) {
                /*job执行异常的时候 执行客户端 fail 方法*/
                try {
                    if (!StringUtils.isEmpty(clock.fail())) {
                        Method fail = jobCallBack.getClass().getMethod(clock.fail());
                        if (fail != null) {
                            fail.invoke(jobCallBack);
                        }
                    }

                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
               // t.setStatus(e);
                try {
                    if (log.isInfoEnabled()) {
                        log.info("business error");
                    }
                    registerService.jobExecutionResult(jobParams.getLogId(), Result.EXECUTE_FAIL.getCode());
                } catch (IOException e1) {
                  //  t.setStatus(e1);
                }
            } finally {
               // t.complete();
            }
        }
    }
}
