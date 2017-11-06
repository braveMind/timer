package com.jun.timer.enums;

/**
 * Created by xunaiyang on 2017/7/16.
 */
public enum LogTypeEnum {
    APP_OFFLINE("0", "应用已下线！"),
    ADDRESS_INVALID("1", "应用地址不可达！"),
    REQUEST_SEND("2", "客户端RPC调用成功！"),
    EXECUTE_FAIL("3", "客户端业务方法调用异常！"),
    EXECUTE_SUCCESS("4", "客户端业务方法调用成功！");

    private String index;
    private String message;

    LogTypeEnum(String index, String message) {
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
        for (LogTypeEnum logType : values()) {
            if (logType.index.equals(index)) {
                return logType.message;
            }
        }
        return null;
    }
}