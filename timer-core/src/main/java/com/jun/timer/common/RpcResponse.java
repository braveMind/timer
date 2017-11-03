package com.jun.timer.common;

import java.io.Serializable;

/**
 * Created by jun
 * 17/7/5 上午10:33.
 * des:
 */
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = -4125725712132627661L;
    private Object result;
    private String message;

    public RpcResponse(Result result, String message) {
        this.result=result;
        this.message=message;
    }

    public RpcResponse() {

    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
