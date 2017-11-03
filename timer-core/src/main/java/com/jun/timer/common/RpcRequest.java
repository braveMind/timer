package com.jun.timer.common;

import java.io.Serializable;

/**
 * Created by jun
 * 17/7/5 上午10:26.
 * des:rpc 请求Request
 */
public class RpcRequest implements Serializable {


    private static final long serialVersionUID = 3490329223156409269L;
    private String requestId;
    private String className;
    private String address;
    private String methodName;
    private Class<?>[] parameterType;
    private Object[] parameter;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object[] getParameter() {
        return parameter;
    }

    public void setParameter(Object[] parameter) {
        this.parameter = parameter;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?>[] parameterTypes) {
        this.parameterType = parameterTypes;
    }
}
