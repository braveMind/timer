package com.jun.timer.dao;

import com.jun.timer.dao.po.LogPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogPOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogPO record);

    int insertSelective(LogPO record);

    LogPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LogPO record);

    int updateByPrimaryKey(LogPO record);

    List<LogPO> selectLogList(@Param("tenantId") String tenantId, @Param("jobNames") List<String> jobNames);
}