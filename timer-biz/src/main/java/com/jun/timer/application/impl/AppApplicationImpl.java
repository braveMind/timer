package com.jun.timer.application.impl;

import com.github.pagehelper.PageInfo;
import com.jun.timer.application.AppApplication;
import com.jun.timer.application.MessageApplication;
import com.jun.timer.constant.ConstantString;
import com.jun.timer.dao.po.AppPO;
import com.jun.timer.dao.po.JobPO;
import com.jun.timer.domain.AppDomain;
import com.jun.timer.domain.JobDomain;
import com.jun.timer.dto.AppDto;
import com.jun.timer.dto.AppInfoDto;
import com.jun.timer.dto.EmailMessageDto;
import com.jun.timer.dto.Page;
import com.jun.timer.utils.CommonUtils;
import com.jun.timer.utils.DefaultThreadPool;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by xunaiyang on 2017/7/10.
 */
@Service
public class AppApplicationImpl implements AppApplication, InitializingBean {
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private static final Logger LOGGER = LoggerFactory.getLogger(AppApplicationImpl.class);

    @Autowired
    private MessageApplication messageApplication;

    @Autowired
    private AppDomain appDomain;

    @Autowired
    private JobDomain jobDomain;


    @Autowired
    private Scheduler quartzScheduler;

    @Override
    public void register(String appName, String address) {
        //LOGGER.info("Application registering:appName[{}], address[{}]", appName, address);
        List<String> addressList = appDomain.getAddressListByName(appName, Boolean.TRUE);
        AppPO appPO = appDomain.getAppInfo(appName, address);
        if (appPO == null) {
            appPO = new AppPO(appName, address, Boolean.TRUE);
            appDomain.addNewApp(appPO);
        } else if (!appPO.getStatus()) {
            changeAppStatus(appPO.getId(), Boolean.TRUE);
        } else {
            appDomain.updateActiveTime(appName, address);
        }
        //LOGGER.info("Application registered");
        if (CollectionUtils.isEmpty(addressList)) {
            List<JobPO> jobPOS = jobDomain.getJobListByAppName(appName);
            try {
                if (CollectionUtils.isNotEmpty(jobPOS)) {
                    for (JobPO jobPO : jobPOS) {
                        quartzScheduler.resumeJob(JobKey.jobKey(jobPO.getJobName(), appName));
                    }
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void unregister(String appName, String address) {
        AppPO appPO = appDomain.getAppInfo(appName, address);
        if (appPO != null) {
            appPO.setStatus(Boolean.FALSE);
            appDomain.updateAppStatus(appPO);
            List<String> addressList = appDomain.getAddressListByName(appName, Boolean.TRUE);
            if (CollectionUtils.isEmpty(addressList)) {
                try {
                    quartzScheduler.pauseJobs(GroupMatcher.groupEquals(appName));
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
                DefaultThreadPool.executor.submit(new ElephantTask(appName));
            }
        }
    }

    @Override
    public void changeAppStatus(Integer id, Boolean status) {
        AppPO appPO = new AppPO();
        appPO.setActiveTime(new Date());
        appPO.setId(id);
        appPO.setStatus(status);
        appDomain.updateAppStatus(appPO);
    }

    @Override
    public AppInfoDto queryAppList(Page page) {
        List<AppPO> appPOList = appDomain.queryAppList(page);
        PageInfo pageInfo = new PageInfo(appPOList);
        AppInfoDto appInfoDto = new AppInfoDto();
        page.setTotalCount((int) pageInfo.getTotal());
        appInfoDto.setPage(page);
        List<AppDto> list = appPOList.stream().map(p -> {
            AppDto appDto = new AppDto();
            BeanUtils.copyProperties(p, appDto);
            return appDto;
        }).collect(Collectors.toList());
        appInfoDto.setAppDtoList(list);
        return appInfoDto;
    }

    @Override
    public List<String> getAddressList(String appName, Boolean status) {
        return appDomain.getAddressListByName(appName, status);
    }

    @Override
    public List<String> getAddress() {
        return appDomain.getAddress();
    }

    @Override
    public void changeActiveTime(String appName, String address) {
        register(appName, address);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(() -> {
            try {
                List<AppPO> appPOList = appDomain.getInvalidApp(new Date(System.currentTimeMillis() - ConstantString.CHECK_TIME));
                if (CollectionUtils.isNotEmpty(appPOList)) {
                    appPOList.forEach(p -> {
                        unregister(p.getAppName(), p.getAddress());
                    });
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }, 60, 60, TimeUnit.SECONDS);
    }

    private class ElephantTask implements Callable {
        String appName;

        public ElephantTask(String appName) {
            this.appName = appName;
        }

        @Override
        public Object call() throws Exception {
            sendElephant(this);
            return null;
        }

        private void sendElephant(ElephantTask t) {
            List<JobPO> jobPOS = jobDomain.getJobListByAppName(appName);
            if (CollectionUtils.isNotEmpty(jobPOS)) {
                jobPOS.forEach(p -> {
                    EmailMessageDto emailMessageDto = new EmailMessageDto("机器下线通知", p.getJobOwner(), p.getId().longValue(),
                            MessageFormat.format(ConstantString.APP_MESSAGE_TEMPLATE, p.getAppName(), CommonUtils.storeName2RealName(p.getJobName())));
                    if (messageApplication != null) {
                        messageApplication.sendAlarmMessage(emailMessageDto);
                    }
                });
            }
        }
    }
}
