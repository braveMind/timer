package com.jun.timer.enums;

/**
 * Created by xunaiyang on 2017/7/17.
 */
public enum JobTypeEnum {
    ONE_TIME_JOB("0", "一次性任务"),
    REPEAT_JOB("1", "周期性任务");

    private String index;
    private String message;

    JobTypeEnum(String index, String message) {
        this.index = index;
        this.message = message;
    }

    public String getIndex() {
        return index;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessage(String index) {
        for (JobTypeEnum jobType : values()) {
            if (jobType.index.equals(index)) {
                return jobType.message;
            }
        }
        return null;
    }
}
