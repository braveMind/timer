package com.jun.timer.dto;

import java.io.Serializable;

/**
 * Created by xunaiyang on 2017/7/6.
 */
public class JobParams implements Serializable {
    private static final long serialVersionUID = 1827968422044515219L;
    private Integer jobId;
    private Integer logId;
    private String className;
    private String methodName;
    private String params;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

}
