package com.jun.timer.utils;



import com.jun.timer.constant.ConstantString;
import com.jun.timer.constant.RemoteUrlInfo;
import com.jun.timer.dto.LogDto;
import com.jun.timer.enums.LogTypeEnum;

import java.text.MessageFormat;
import java.util.UUID;

/**
 * Created by xunaiyang on 2017/7/16.
 */
public class CommonUtils {

    public static String getLogInfo(LogDto logDto) {
        return MessageFormat.format(ConstantString.LOG_TEMPLATE, logDto.getAppName(),
                storeName2RealName(logDto.getJobName()), logDto.getClassName(), logDto.getClassName(), logDto.getParams(), LogTypeEnum.getMessage(logDto.getLogType()));
    }

    public static String realName2StoreName(String tenantId, String realName) {
        StringBuilder sb = new StringBuilder();
        return sb.append(tenantId).append('_').append(realName).append('_').append(UUID.randomUUID()).toString();
    }

    public static String storeName2RealName(String storeName) {
        return storeName.substring(storeName.indexOf('_') + 1, storeName.lastIndexOf('_'));
    }

    public static String getTenantId(String storeName) {
        return storeName.substring(0, storeName.indexOf('_'));
    }
}
