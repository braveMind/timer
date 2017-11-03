package com.jun.timer.utils;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by jun
 * 17/7/19 下午3:03.
 * des:
 */
public class HttpResult<T> implements Serializable {
    private static final long serialVersionUID = 8104635022774506825L;

    public static final Integer TRUE = 1;
    public static final Integer FALSE = 0;
    public static final Integer SUCCESS = 200;
    public static final Integer FAILED = 500;
    public static final Integer WARNING = 600;
    public static final Integer FORBIDDEN = 403;

    private static final String DEFAULT_SUCCESS_MSG = "成功";
    private static final String DEFAULT_FAILED_MSG = "失败,原因未知";
    private static final String MAP_KEY_DATA = "data";
    private static final String MAP_KEY_STATUS = "status";
    private static final String MAP_KEY_MESSAGE = "message";
    private static final String MAP_KEY_DATA_ERROR_CODE = "errorCode";
    private static final String MAP_KEY_DATA_MSG = "message";
    private static final String BUILD_DATA_NULL = "data不能为空";
    private static final String BUILD_STATUS_NULL = "status不能为空";
    private static final String BUILD_MESSAGE_NULL = "message不能为空";


    private T data;
    private Integer status;
    private String message;

    public static Integer getSUCCESS() {
        return SUCCESS;
    }

    public static Integer getFAILED() {
        return FAILED;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static <T> Builder<T> builder() {
        return new Builder<T>();
    }

    public static class Builder<T> {
        private T data;
        private Integer status;
        private String message;

        public Builder() {}

        public HttpResult build() {
            HttpResult<T> result = new HttpResult<T>();
            result.setData(data);
            result.setStatus(status);
            result.setMessage(message);
            return result;
        }

        public Map<String,T> resultOnlyData(){
            Map<String,T> result = Maps.newHashMap();
            result.put(MAP_KEY_DATA,data);
            return result;
        }

        public HttpResult resultEmpty() {

            HttpResult<T> result = new HttpResult<T>();
            result.setData(data);
            return result;
        }


        public Builder data(T data, Integer status, String message){
            this.data = data;
            this.status = status;
            this.message = message;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public Builder failed() {
            this.status = FAILED;
            this.message = DEFAULT_FAILED_MSG;
            return this;
        }

        public Builder failedFalse() {
            this.status = FALSE;
            this.message = DEFAULT_FAILED_MSG;
            return this;
        }

        public Builder failed(String message) {
            this.status = FAILED;
            this.message = message;
            return this;
        }

        public Builder failedFalse(String message) {
            this.status = FALSE;
            this.message = message;
            return this;
        }

        public Builder failed(Integer status, String message) {
            this.status = status;
            this.message = message;
            return this;
        }

        public Builder success() {
            this.status = SUCCESS;
            this.message = DEFAULT_SUCCESS_MSG;
            return this;
        }

        public Builder successTrue() {
            this.status = TRUE;
            this.message = DEFAULT_SUCCESS_MSG;
            return this;
        }

        public Builder successTrue(String message) {
            this.status = TRUE;
            this.message = message;
            return this;
        }

        public Builder success(Integer status, String msg) {
            this.status = status;
            this.message = msg;
            return this;
        }

        public Builder success(String message) {
            this.status = SUCCESS;
            this.message = message;
            return this;
        }

        public Builder warning(String message) {
            this.status = WARNING;
            this.message = message;
            return this;
        }

        public Builder warning(Integer status, String message) {
            this.status = status;
            this.message = message;
            return this;
        }

        public Builder data( Integer errorCode, String message){
            Map<String,Object> dataMap = Maps.newHashMap();
            dataMap.put(MAP_KEY_DATA_ERROR_CODE,errorCode);
            dataMap.put(MAP_KEY_DATA_MSG,message);
            this.data = (T)dataMap;
            return this;
        }
    }

    public HttpResult() {}
}
