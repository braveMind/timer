package com.jun.timer.application.impl;

import com.github.pagehelper.PageInfo;

import com.jun.timer.application.LogApplication;
import com.jun.timer.dao.po.LogPO;
import com.jun.timer.domain.JobDomain;
import com.jun.timer.domain.LogDomain;
import com.jun.timer.dto.LogDto;
import com.jun.timer.dto.LogResultDto;
import com.jun.timer.dto.Page;
import com.jun.timer.enums.LogTypeEnum;
import com.jun.timer.utils.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xunaiyang on 2017/7/12.
 */
@Service
public class LogApplicationImpl implements LogApplication {
    @Autowired
    private LogDomain logDomain;

    @Autowired
    private JobDomain jobDomain;

    @Override
    public LogResultDto queryLog(String tenantId, Page page, String jobOwner) {
        List<String> jobNames = jobDomain.getJobNameByOwner(tenantId, jobOwner);
        List<LogPO> logPOS = logDomain.getLogList(tenantId, jobNames, page);
        List<LogDto> logDtos = new ArrayList<>();
        PageInfo pageInfo = new PageInfo(logPOS);
        page.setTotalCount((int)pageInfo.getTotal());
        if(CollectionUtils.isNotEmpty(logPOS)) {
            logDtos = logPOS.stream().map(p->{
                LogDto logDto = new LogDto();
                BeanUtils.copyProperties(p, logDto);
                logDto.setJobName(CommonUtils.storeName2RealName(p.getJobName()));
                logDto.setResult(LogTypeEnum.getMessage(p.getLogType()));
                return logDto;
            }).collect(Collectors.toList());
        }
        LogResultDto logResultDto= new LogResultDto();
        logResultDto.setLogDtoList(logDtos);
        logResultDto.setPage(page);
        return logResultDto;
    }

    @Override
    public Integer recordLogInfo(String tenantId, LogDto logDto) {
        LogPO logPO = new LogPO();
        BeanUtils.copyProperties(logDto, logPO);
        logPO.setTenantId(tenantId);
        return logDomain.recordLogInfo(logPO);
    }

    @Override
    public void modifyLogInfo(LogDto logDto) {
        LogPO logPO = new LogPO();
        BeanUtils.copyProperties(logDto, logPO);
        logDomain.updateLogById(logPO);
    }

    @Override
    public LogDto getLogInfoById(Integer logId) {
        LogPO logPO = logDomain.getLogInfoById(logId);
        if(logPO != null) {
            LogDto logDto = new LogDto();
            BeanUtils.copyProperties(logPO, logDto);
            return logDto;
        }
        return null;
    }
}
