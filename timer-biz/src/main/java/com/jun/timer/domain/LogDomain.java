package com.jun.timer.domain;



import com.jun.timer.dao.po.LogPO;
import com.jun.timer.dto.Page;

import java.util.List;

/**
 * Created by xunaiyang on 2017/7/12.
 */
public interface LogDomain {
    List<LogPO> getLogList(String tenantId, List<String> jobNames, Page page);

    Integer recordLogInfo(LogPO logPO);

    Integer updateLogById(LogPO logPO);

    LogPO getLogInfoById(Integer id);

}
