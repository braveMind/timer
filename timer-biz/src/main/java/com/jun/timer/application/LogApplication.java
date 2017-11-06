package com.jun.timer.application;


import com.jun.timer.dto.LogDto;
import com.jun.timer.dto.LogResultDto;
import com.jun.timer.dto.Page;

/**
 * Created by xunaiyang on 2017/7/12.
 */
public interface LogApplication {
    /**
     * 获取日志列表
     * @param page
     * @return
     */
    LogResultDto queryLog(String tenantId, Page page, String jobOwner);

    /**
     * 记录job执行日志
     * @param logDto
     */
    Integer recordLogInfo(String tenantId, LogDto logDto);

    /**
     * 修改log执行日志
     * @param logDto
     */
    void modifyLogInfo(LogDto logDto);

    LogDto getLogInfoById(Integer logId);
}
