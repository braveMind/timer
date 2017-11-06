package com.jun.timer.router;



import com.jun.timer.biz.ExecutorBiz;
import com.jun.timer.biz.impl.ExecutorBizProxy;
import com.jun.timer.common.RpcResponse;
import com.jun.timer.dto.JobParams;
import com.jun.timer.dto.LogDto;

import java.util.List;

/**
 * Created by xunaiyang on 2017/7/16.
 */
public abstract class BaseRouter {
    /**
     * 具体的路由策略，由子类实现，返回执行器地址
     * @param jobId
     * @param addressList
     * @return
     */
    public abstract String routeStrategy(Integer jobId, List<String> addressList);

    /**
     * 路由运行逻辑，子类根据不同的路由策略实现
     * @param jobParams
     * @param addressList
     * @return
     */
    public abstract RpcResponse routeRun(JobParams jobParams, List<String> addressList, LogDto logDto);

    /**
     * 服务端执行器执行方法，所有的子类共用
     * @param jobParams
     * @param address
     * @return
     */
    protected RpcResponse runExcutor(JobParams jobParams, String address) {
        //TODO 先做心跳检测
        ExecutorBiz executorBiz = (ExecutorBiz)new ExecutorBizProxy(ExecutorBiz.class, address).getObject();
        return executorBiz.run(jobParams);
    }

    /**
     * 心跳检查，检查执行器当前是否可达
     * @param address
     * @return
     */
    protected RpcResponse runHeartBeat(String address) {
        ExecutorBiz executorBiz = (ExecutorBiz)new ExecutorBizProxy(ExecutorBiz.class, address).getObject();
        return executorBiz.heartBeat();
    }
}
