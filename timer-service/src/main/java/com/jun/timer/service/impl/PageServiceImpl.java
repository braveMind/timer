package com.jun.timer.service.impl;


import com.jun.timer.application.AppApplication;
import com.jun.timer.application.JobApplication;
import com.jun.timer.application.LogApplication;
import com.jun.timer.constant.ConstantString;
import com.jun.timer.constant.RemoteUrlInfo;
import com.jun.timer.dto.*;
import com.jun.timer.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xunaiyang on 2017/9/20.
 */
@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private AppApplication appApplication;

    @Autowired
    private JobApplication jobApplication;

    @Autowired
    private LogApplication logApplication;
    //没接配置中心 先写死
    private static final String tenant= ConstantString.tenant;

    @Override
    public ListResult jobList(String tenantId, Page page, String jobOwner) {
        JobResultDto jobResultDto = jobApplication.getJobList(tenant, page, jobOwner);
        ListResult listResult=new ListResult(jobResultDto.getJobDtoList(), page.getTotalCount());
        return listResult;
    }

    @Override
    public ListResult machine(String tenantId, Page page) {
        AppInfoDto appInfoDto = appApplication.queryAppList(page);
        ListResult listResult = new ListResult(appInfoDto.getAppDtoList(), appInfoDto.getPage().getTotalCount());
        return listResult;
    }

    @Override
    public ListResult diaryList(String tenantId, Page page, String jobOwner) {
        LogResultDto logResultDto = logApplication.queryLog(tenant, page, jobOwner);
        ListResult listResult = new ListResult(logResultDto.getLogDtoList(), logResultDto.getPage().getTotalCount());
        return listResult;
    }
}
