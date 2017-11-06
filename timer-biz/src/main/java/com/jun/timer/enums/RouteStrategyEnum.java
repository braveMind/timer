package com.jun.timer.enums;


import com.jun.timer.router.BaseRouter;
import com.jun.timer.router.Strategy.*;

/**
 * Created by xunaiyang on 2017/9/11.
 */
public enum RouteStrategyEnum {

    ROUTE_BY_FIRST(0, "第一个", new RouteByFirst()),
    ROUTE_BY_LAST(1, "最后一个", new RouteByLast()),
    ROUTE_BY_ROUND(2, "轮寻", new RouteByRound()),
    ROUTE_BY_RANDOW(3, "随机", new RouteByRandom()),
    ROUTE_BY_CONSISTENT_HASH(4, "一致性HASH", new RouteByConsistentHash()),
    ROUTE_BY_FAILOVER(5, "故障转移", new RouteByFailover());

    private int type;
    private String description;
    private BaseRouter router;

    RouteStrategyEnum(int type, String description, BaseRouter router) {
        this.type = type;
        this.description = description;
        this.router = router;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public BaseRouter getRouter() {
        return router;
    }

    public static BaseRouter getRouter(int type) {
        for (RouteStrategyEnum routeStrategy : values()) {
            if (routeStrategy.type == type) {
                return routeStrategy.router;
            }
        }
        return null;
    }
}
