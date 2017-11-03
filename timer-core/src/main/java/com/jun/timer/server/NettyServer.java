package com.jun.timer.server;

import com.jun.timer.biz.ExecutorBiz;
import com.jun.timer.biz.impl.ExecutorBizImpl;
import com.jun.timer.handler.NettyChannelRead;
import com.jun.timer.handler.ServerHandlerMapping;
import com.jun.timer.serialize.RpcDecoder;
import com.jun.timer.serialize.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by jun
 * 17/7/5 上午10:49.
 * des:rpc 服务端
 * 两边互为服务器
 */
public class NettyServer implements ServerService {
    Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private String address;
    private int port;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private final ExecutorBiz executorBiz=new ExecutorBizImpl();


    public NettyServer(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void start() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new RpcDecoder())
                                    .addLast(new RpcEncoder())
                                    .addLast(new NettyChannelRead(new ServerHandlerMapping(executorBiz)));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            if (logger.isInfoEnabled()) logger.info("[{}]:[{}]RPC start success...", address, port);
            ChannelFuture future = bootstrap.bind(address, port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            try {
                executorBiz.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        try {
            executorBiz.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NettyServer("127.0.0.1", 9912).start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
