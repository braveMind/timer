package com.jun.timer.dao.po;

import java.util.Date;

public class AppPO {
    private Integer id;

    private String appName;

    private String address;

    private Boolean status;

    private Date activeTime;

    public AppPO() {

    }

    public AppPO(String appName, String address, Boolean status) {
        this.appName = appName;
        this.address = address;
        this.status = status;
    }

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
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }
}