package com.jun.timer.dto;



import java.io.Serializable;
import java.util.List;

/**
 * Created by xunaiyang on 2017/7/12.
 */
public class LogResultDto implements Serializable {
    private List<LogDto> logDtoList;
    private Page page;

    public List<LogDto> getLogDtoList() {
        return logDtoList;
    }

    public void setLogDtoList(List<LogDto> logDtoList) {
        this.logDtoList = logDtoList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
