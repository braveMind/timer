package com.jun.timer.client;

import com.jun.timer.common.ClientException;
import com.jun.timer.common.Result;
import com.jun.timer.common.RpcRequest;
import com.jun.timer.common.RpcResponse;
import com.jun.timer.serialize.RpcDecoder;
import com.jun.timer.serialize.RpcEncoder;
import com.jun.timer.utils.IPUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jun
 * 17/7/23 上午8:57.
 * des:新增缓存功能,系统启动驻留在后端运行
 *
 */
public class NettyClient extends AbstractClient {
    private final Bootstrap bootstrap = new Bootstrap();
    private ClientConfig clientConfig;
    private final ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    private NettyClient(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
        eventLoopGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("Event Group", this.threadIndex.incrementAndGet()));
            }
        });
    }

    /*同步发送,*/
    public RpcResponse send(RpcRequest request, String ip, int port) throws ClientException {
        Channel channel = getChannel(ip,port);
        if (channel == null) {
            logger.error("get channel error[{}---{}]", ip, port);
            throw new ClientException(ip, null);
        }
        try {
            ChannelFuture future = channel.writeAndFlush(request).await();
            if (future.isSuccess()) {
                return new RpcResponse(Result.SUCCESS, "客户端RPC调用成功！");
            } else {
                logger.info("客户端调用失败");
                return new RpcResponse(Result.FAIL, "客户端RPC调用失败！");
            }
        } catch (InterruptedException e) {
            throw new ClientException(ip, e);
        }
    }

    public void sendAsync(RpcRequest request, String ip, int port, CallBackService callBackService) throws ClientException {
        Channel channel = channelMap.get(ip + ":" + port);
        if (channel != null) {
            logger.error("get channel error[{}---{}]", ip, port);
            try {
                throw new ClientException(ip, null);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        try {
            channel.writeAndFlush(request).addListener(new GenericFutureListener() {
                @Override
                public void operationComplete(Future future) throws Exception {
                    callBackService.callBack(future);
                }
            });
        } catch (Exception e) {
            logger.error("send message", e);
            throw new ClientException(ip, e);

        }
    }


    @Override
    protected int getThreadCount() {
        return clientConfig.getEventThread();
    }

    @Override
    public void shutdown() {
        logger.info("Shutdown netty  client...");
        try {
            closeChannels();
            this.eventLoopGroup.shutdownGracefully();

            if (this.defaultEventExecutorGroup != null) {
                this.defaultEventExecutorGroup.shutdownGracefully();
            }
        } catch (Exception e) {
            logger.error("NettyRemotingClient shutdown exception, ", e);
        }
    }

    private void closeChannels() {
        Collection<Channel> collection = channelMap.values();
        collection.forEach(p -> p.close());
        collection.clear();
    }

    private Channel getChannel(String address, int port) {
        if (address == null ) {
            return null;
        }
        Channel channel = channelMap.get(address + ":" + port);
        if (channel == null || !channel.isActive()) {
            channelMap.remove(address);
            /*create new channel*/
            return createNewChannel(address, port);
        }
        return channel;
    }

    @Override
    public void start() {
        super.start();
        /*公用EventLoopGroup,selector*/
        bootstrap.group(this.eventLoopGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfig.getConnectTimeout())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast(defaultEventExecutorGroup,/*多线程处理Handler*/
                                        new RpcDecoder(),
                                        new RpcEncoder(),
                                        new HeartbeatServerHandler(0, 30, 0, TimeUnit.MINUTES),
                        new ChannelManger());//发生异常清理连接
                    }
                });
    }

    class HeartbeatServerHandler extends IdleStateHandler {


        public HeartbeatServerHandler(long readerIdleTime, long writerIdleTime,long allIdleTime,TimeUnit timeUnit) {
            super(readerIdleTime, writerIdleTime, allIdleTime, timeUnit);
        }

        @Override
        protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
            if(evt.state()==IdleState.WRITER_IDLE){
                String address= IPUtils.parseChannelRemoteAddr1(ctx.channel());
                 if(address!=null){
                     Channel channel=channelMap.remove(address);
                     ctx.channel().close();
                 }
            }
        }
    }

    class ChannelManger extends ChannelDuplexHandler {
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            final String remoteAddress = IPUtils.parseChannelRemoteAddr1(ctx.channel());
            logger.warn("NETTY SERVER PIPELINE: exceptionCaught {}", remoteAddress);
            logger.warn("NETTY SERVER PIPELINE: exceptionCaught exception.", cause);

            Channel channel = channelMap.remove(remoteAddress);
            if (channel != null) {
                logger.info(remoteAddress + " close connection");
            }
            ctx.channel().close();
        }
    }

    private Channel createNewChannel(String address, int port) {
        ChannelFuture future;
        try {
            future = bootstrap.connect(new InetSocketAddress(address, port));
            future.awaitUninterruptibly();
        } catch (Exception e) {
            logger.info("Connect to TargetServer encounter error.", e);
            return null;
        }

        if (future.isSuccess()) {
            Channel channel = future.channel();
            channelMap.put(address + ":" + port, channel);
            return channel;
        } else {
            logger.error("Connect to TargetServer failed.", future.cause());
            return null;
        }
    }
}
