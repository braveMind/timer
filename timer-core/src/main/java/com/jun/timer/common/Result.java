package com.jun.timer.common;

/**
 * Created by jun
 * 17/7/5 下午2:05.
 * des:
 */
public enum Result {
    SUCCESS(200),
    FAIL(500),
    EXECUTE_FAIL(3),
    EXECUTE_SUCCESS(4);
    private int code;

    Result(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
