package com.jun.timer.application;



import com.jun.timer.dto.AppInfoDto;
import com.jun.timer.dto.Page;

import java.util.List;

/**
 * Created by xunaiyang on 2017/7/10.
 */
public interface AppApplication {
    /**
     * 客户端服务注册 注册前检查应用是否存在可用的地址,
     * 若不存在，则在注册完后恢复该应用下创建的状态为true的Job
     * @param appName
     * @param address
     */
    public void register(String appName, String address);

    /**
     * 客户端撤销注册 撤销后检查应用是否存在可用的地址，
     * 若不存在，则在撤销后暂停该应用下创建的所有Job
     * @param appName
     * @param address
     */
    public void unregister(String appName, String address);

    /**
     * 更改客户端连接状态
     * @param id
     * @param status
     */
    public void changeAppStatus(Integer id, Boolean status);
    /**
     * 查询服务列表
     * @param page
     * @return
     */
    AppInfoDto queryAppList(Page page);

    /**
     * 根据应用名查询客户端地址
     * @param appName
     * @return
     */
    List<String> getAddressList(String appName, Boolean status);

    List<String> getAddress();

    void changeActiveTime(String appName, String address);

}
