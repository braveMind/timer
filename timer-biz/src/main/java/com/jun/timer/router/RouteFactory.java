package com.jun.timer.router;

import com.google.common.collect.Maps;

import com.jun.timer.enums.RouteStrategyEnum;
import com.jun.timer.router.Strategy.*;

import java.util.Map;

/**
 * @author xunaiyang
 * @date 2018/1/10
 */
public class RouteFactory {
    private static Map<RouteStrategyEnum, BaseRouter> cache = Maps.newConcurrentMap();

    public static BaseRouter getRouter(RouteStrategyEnum routeStrategy) {
        if(!cache.containsKey(routeStrategy)) {
            switch (routeStrategy) {
                case ROUTE_BY_FIRST:
                    cache.put(routeStrategy, new RouteByFirst());
                    break;
                case ROUTE_BY_LAST:
                    cache.put(routeStrategy, new RouteByLast());
                    break;
                case ROUTE_BY_ROUND:
                    cache.put(routeStrategy, new RouteByRound());
                    break;
                case ROUTE_BY_RANDOW:
                    cache.put(routeStrategy, new RouteByRandom());
                    break;
                case ROUTE_BY_CONSISTENT_HASH:
                    cache.put(routeStrategy, new RouteByConsistentHash());
                    break;
                case ROUTE_BY_FAILOVER:
                    cache.put(routeStrategy, new RouteByFailover());
                    break;
                case ROUTE_BY_TARGET:
                    cache.put(routeStrategy, new RouteByTarget());
                    break;
                default:
                    cache.put(routeStrategy, new RouteByFailover());
            }
        }
        return cache.get(routeStrategy);
    }
}
