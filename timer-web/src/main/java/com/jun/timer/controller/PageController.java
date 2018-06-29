package com.jun.timer.controller;


import com.jun.timer.constant.ConstantString;
import com.jun.timer.constant.RemoteUrlInfo;
import com.jun.timer.dto.ListResult;
import com.jun.timer.dto.Page;
import com.jun.timer.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xunaiyang on 2017/7/5.
 */

@Controller
@RequestMapping("/")
public class PageController {
    private static final String tenantId = ConstantString.tenant;
    @Autowired
    private PageService pageService;

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/jobList")
    public String jobList() {
        return "jobList";
    }

    @RequestMapping(value = "/jobPage/")
    @ResponseBody
    public ListResult jobList(String query, Integer pageIndex, Integer pageSize) {
        Page page = new Page(pageIndex + 1, pageSize);

        return pageService.jobList(tenantId, page, null);
    }

    /**
     * 服务列表
     *
     * @return
     */
    @RequestMapping(value = "/machine")
    public String machine() {
        return "machineList";
    }

    @RequestMapping(value = "/machineList/")
    @ResponseBody
    public ListResult machine(String query, Integer pageIndex, Integer pageSize) {
        Page page = new Page(pageIndex + 1, pageSize);
        return pageService.machine(tenantId, page);
    }

    @RequestMapping(value = "/diary")
    public String diary() {
        return "diary";
    }

    @RequestMapping(value = "/diaryList/")
    @ResponseBody
    public ListResult diaryList(String query, Integer pageIndex, Integer pageSize) {
        Page page = new Page(pageIndex + 1, pageSize);

        return pageService.diaryList(tenantId, page, null);
    }
}
