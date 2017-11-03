package com.jun.timer.utils;

import com.dianping.lion.client.ConfigCache;
import com.dianping.lion.client.LionException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LionUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(LionUtils.class);

    private static ConfigCache configCache;

    static {
        try {
            configCache = ConfigCache.getInstance();
        } catch (LionException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }
    }

    public static String getLionValue(String key) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("lion key can not be null!");
        }
        return configCache.getProperty(key);
    }

    public static String getLionValue(String key, String defaultValue) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("lion key can not be null!");
        }
        try {
            return configCache.getProperty(key);
        } catch (LionException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            return defaultValue;
        }
    }


    public static Boolean getLionBooleanValue(String key) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("lion key can not be null!");
        }
        return configCache.getBooleanProperty(key);
    }

    public static Integer getLionIntegerValue(String key) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("lion key can not be null!");
        }
        return configCache.getIntProperty(key);
    }

    public static Boolean getLionBooleanValue(String key, boolean defaultValue) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("lion key can not be null!");
        }
        try {
            return configCache.getBooleanProperty(key);
        } catch (LionException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            return defaultValue;
        }
    }

    public static Integer getLionIntegerValue(String key, int defaultValue) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("lion key can not be null!");
        }
        try {
            return configCache.getIntProperty(key);
        } catch (LionException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            return defaultValue;
        }
    }

}


