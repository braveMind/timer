package com.jun.timer.domain;


import com.jun.timer.dao.po.AppPO;
import com.jun.timer.dto.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by xunaiyang on 2017/7/10.
 */
public interface AppDomain {
    /**
     * 查询App信息
     * @param appName
     * @param address
     * @return
     */
    AppPO getAppInfo(String appName, String address);
    /**
     * 添加app信息
     * @param appPO
     */
    void addNewApp(AppPO appPO);

    /**
     * 删除App信息
     * @param id app主键
     */
    void deleteApp(Integer id);

    /**
     * 修改app状态
     * @param appPO
     */
    void updateAppStatus(AppPO appPO);

    /**
     * 分页查询应用信息
     * @param page
     * @return
     */
    List<AppPO>queryAppList(Page page);

    /**
     * 查询指定应用对应的地址
     * @param appName
     * @return
     */
    List<String> getAddressListByName(String appName, Boolean status);

    /**
     * 获取所address
     * @return
     */
    List<String> getAddress();

    /**
     * 更新应用最后一次活跃时间
     * @param appName
     * @return
     */
    int updateActiveTime(String appName, String address);

    /**
     * 获取心跳检查超时的应用列表
     * @param date
     * @return
     */
    List<AppPO> getInvalidApp(Date date);
}
