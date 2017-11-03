package com.jun.timer.client;

import com.iph.zhaopin.rpc.common.NettyService;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jun
 * 17/7/23 上午9:00.
 * des:
 */
public abstract class AbstractClient implements NettyService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected EventLoopGroup eventLoopGroup;

    protected DefaultEventExecutorGroup defaultEventExecutorGroup;

    protected abstract int getThreadCount();

    /*提供默认的EventExecutorGroup*/
    @Override
    public void start() {
        this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(
                getThreadCount(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                return new Thread(r, "Netty Default Event Client Thread.." + this.threadIndex.incrementAndGet());
            }
        });
    }
}
