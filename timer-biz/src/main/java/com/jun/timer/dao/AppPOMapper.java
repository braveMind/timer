package com.jun.timer.dao;

import com.jun.timer.dao.po.AppPO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AppPOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppPO record);

    int insertSelective(AppPO record);

    AppPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppPO record);

    int updateByPrimaryKey(AppPO record);

    AppPO selectByNameAndAddress(@Param("appName") String appName, @Param("address") String address);
    List<AppPO> selectAppList();

    List<String> getAddressByAppName(@Param("appName") String appName, @Param("status") Boolean status);

    List<String> getAddress();

    int updateActiveTimeByApp(@Param("appName") String appName,
                              @Param("activeTime") Date activeTime,
                              @Param("address") String address);
    List<AppPO> getInvalidAppList(@Param("activeTime") Date activeTime);
}