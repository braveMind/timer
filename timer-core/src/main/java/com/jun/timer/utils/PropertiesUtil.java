package com.jun.timer.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtil {
    private static Properties config = null;
    static Log log = LogFactory.getLog(PropertiesUtil.class);
    static {
        if (config == null) {
            InputStream is = null;
            try {
                is = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getFile() + "/META-INF/app.properties");
                config = new Properties();
                config.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getPropertyValueByKey(String key) {
        return config.getProperty(key);
    }

}
