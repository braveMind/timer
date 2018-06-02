package com.jun.timer.dao;

import com.jun.timer.dao.po.JobPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobPOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JobPO record);

    int insertSelective(JobPO record);

    JobPO selectByPrimaryKey(Integer id);

    JobPO selectByJobAndApp(@Param("jobName") String jobName, @Param("appName") String appName);

    int updateByPrimaryKeySelective(JobPO record);

    int updateByPrimaryKey(JobPO record);

    int updateStatus(@Param("id") Integer id, @Param("status") Boolean status);

    List<JobPO> selectJobList(@Param("tenantId") String tenantId/*,@Param("jobOwner") String jobOwner*/);

    List<JobPO> selectJobListByAppName(@Param("appName") String appName);

    List<String> selectJobNameByJobOwner(@Param("tenantId") String tenantId, @Param("jobOwner") String jobOwner);
}