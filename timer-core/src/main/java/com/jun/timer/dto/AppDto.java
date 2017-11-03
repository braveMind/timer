package com.jun.timer.dto;

import java.io.Serializable;

/**
 * Created by xunaiyang on 2017/9/21.
 */
public class AppDto implements Serializable {
    private Integer id;

    private String appName;

    private String address;

    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
