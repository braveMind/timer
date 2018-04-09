package com.jun.timer.enums;


/**
 * Created by xunaiyang on 2017/9/11.
 */

public enum RouteStrategyEnum {

    ROUTE_BY_FIRST(0, "第一个"),
    ROUTE_BY_LAST(1, "最后一个"),
    ROUTE_BY_ROUND(2, "轮寻"),
    ROUTE_BY_RANDOW(3, "随机"),
    ROUTE_BY_CONSISTENT_HASH(4, "一致性HASH"),
    ROUTE_BY_FAILOVER(5, "故障转移"),
    @Deprecated
    // 仅限调试时候使用！！！
    ROUTE_BY_TARGET(6, "指定机器");

    private int type;
    private String description;

    RouteStrategyEnum(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }


    public static RouteStrategyEnum getStrategyEnum(int type) {
        for (RouteStrategyEnum routeStrategy : values()) {
            if (routeStrategy.type == type) {
                return routeStrategy;
            }
        }
        return null;
    }
}


