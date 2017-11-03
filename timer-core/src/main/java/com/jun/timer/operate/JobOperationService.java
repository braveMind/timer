package com.jun.timer.operate;


import com.jun.timer.dto.JobDto;

import java.io.IOException;
import java.util.Date;

/**
 * Created by xunaiyang on 2017/7/17.
 */
public interface JobOperationService {
    /**
     * 客户端创建一次性job 创建失败返回-1
     * @param tenantId 租户ID
     * @param jobDto
     * @return 所创Job的Id
     * @throws IOException
     */
    Integer addJob(String tenantId, JobDto jobDto, Date time);

    /**
     * 客户端修改一次性job
     * @param tenantId 租户ID
     * @param jobId
     * @param time
     * @return
     */
    Boolean modifyJob(String tenantId, Integer jobId, Date time);

    /**
     * 客户端删除一次性job
     * @param tenantId 租户ID
     * @param jobId
     * @return
     * @throws IOException
     */
    Boolean deleteJob(String tenantId, Integer jobId);

    /**
     * 客户端暂停一次性job
     * @param tenantId 租户ID
     * @param jobId
     * @return
     * @throws IOException
     */
    Boolean pauseJob(String tenantId, Integer jobId);

    /**
     * 客户端恢复一次性job
     * @param tenantId 租户ID
     * @param jobId
     * @return
     * @throws IOException
     */
    Boolean resumeJob(String tenantId, Integer jobId);

    /**
     * 客户端立即触发任务
     * @param tenantId 租户ID
     * @param jobId
     * @return
     */
    Boolean triggerJob(String tenantId, String jobId);
}
