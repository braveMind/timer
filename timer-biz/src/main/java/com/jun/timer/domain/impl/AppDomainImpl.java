package com.jun.timer.domain.impl;

import com.github.pagehelper.PageHelper;

import com.jun.timer.dao.AppPOMapper;
import com.jun.timer.dao.po.AppPO;
import com.jun.timer.domain.AppDomain;
import com.jun.timer.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by xunaiyang on 2017/7/10.
 */
@Component
public class AppDomainImpl implements AppDomain {
    @Autowired
    private AppPOMapper appPOMapper;

    @Override
    public AppPO getAppInfo(String appName, String address) {
        return appPOMapper.selectByNameAndAddress(appName, address);
    }

    @Override
    public void addNewApp(AppPO appPO) {
        appPOMapper.insertSelective(appPO);
    }

    @Override
    public void deleteApp(Integer id) {
        appPOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateAppStatus(AppPO appPO) {
        appPOMapper.updateByPrimaryKeySelective(appPO);
    }

    @Override
    public List<AppPO> queryAppList(Page page) {
        PageHelper.startPage(page.getPageNo(),page.getPageSize());
        List list=appPOMapper.selectAppList();
        return list;
    }

    @Override
    public List<String> getAddressListByName(String appName, Boolean status) {
        return appPOMapper.getAddressByAppName(appName, status);
    }

    @Override
    public List<String> getAddress() {
         return appPOMapper.getAddress();
    }

    @Override
    public int updateActiveTime(String appName, String address) {
        return appPOMapper.updateActiveTimeByApp(appName, new Date(), address);
    }

    @Override
    public List<AppPO> getInvalidApp(Date date) {
        return appPOMapper.getInvalidAppList(date);
    }
}
