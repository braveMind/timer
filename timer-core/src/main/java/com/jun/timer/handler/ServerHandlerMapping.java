package com.jun.timer.handler;

import com.iph.zhaopin.rpc.biz.ExecutorBiz;
import com.iph.zhaopin.rpc.common.Result;
import com.iph.zhaopin.rpc.common.RpcRequest;
import com.iph.zhaopin.rpc.common.RpcResponse;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by jun
 * 17/7/5 下午3:16.
 * des:
 */
public class ServerHandlerMapping implements Handler<RpcResponse, RpcRequest> {
    private ExecutorBiz executorBiz;

    public ServerHandlerMapping(ExecutorBiz executorBiz) {
        this.executorBiz=executorBiz;
    }

    @Override
    public RpcResponse handle(RpcRequest rpcRequest) {

        try {

            return (RpcResponse) executorBiz.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterType()).invoke(executorBiz, rpcRequest.getParameter());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new RpcResponse(Result.FAIL, "客户端PRC调用失败！");
    }

}
