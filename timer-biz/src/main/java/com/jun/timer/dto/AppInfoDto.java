package com.jun.timer.dto;



import java.io.Serializable;
import java.util.List;

/**
 * Created by jun
 * 17/7/11 下午5:33.
 * des:
 */
public class AppInfoDto implements Serializable {
    public List<AppDto> appDtoList;
    public Page page;

    public List<AppDto> getAppDtoList() {
        return appDtoList;
    }

    public void setAppDtoList(List<AppDto> appDtoList) {
        this.appDtoList = appDtoList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
