package com.jun.timer.service;


import com.jun.timer.dto.ListResult;
import com.jun.timer.dto.Page;

/**
 * Created by xunaiyang on 2017/9/21.
 */
public interface PageService {
    ListResult jobList(String tenantId, Page page, String jobOwner);

    ListResult machine(String tenantId, Page page);

    ListResult diaryList(String tenantId, Page page, String jobOwner);
}
