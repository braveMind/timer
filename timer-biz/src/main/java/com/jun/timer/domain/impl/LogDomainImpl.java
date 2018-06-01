package com.jun.timer.domain.impl;

import com.github.pagehelper.PageHelper;

import com.jun.timer.constant.ConstantString;
import com.jun.timer.dao.LogPOMapper;
import com.jun.timer.dao.po.LogPO;
import com.jun.timer.domain.LogDomain;
import com.jun.timer.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xunaiyang on 2017/7/12.
 */
@Service
public class LogDomainImpl implements LogDomain {
    @Autowired
    private LogPOMapper logPOMapper;

    @Override
    public List<LogPO> getLogList(String tenantId, List<String> jobNames, Page page) {
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        return logPOMapper.selectLogList(ConstantString.tenant, jobNames);
    }

    @Override
    public Integer recordLogInfo(LogPO logPO) {
        logPOMapper.insertSelective(logPO);
        return logPO.getId();
    }

    @Override
    public Integer updateLogById(LogPO logPO) {
        return logPOMapper.updateByPrimaryKeySelective(logPO);
    }

    @Override
    public LogPO getLogInfoById(Integer id) {
        return logPOMapper.selectByPrimaryKey(id);
    }
}
