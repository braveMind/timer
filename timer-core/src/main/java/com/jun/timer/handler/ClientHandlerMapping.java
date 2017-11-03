package com.jun.timer.handler;


import com.jun.timer.common.RpcRequest;
import com.jun.timer.common.RpcResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jun
 * 17/7/6 下午8:57.
 * des:采用
 */
public class ClientHandlerMapping extends SimpleChannelInboundHandler<RpcResponse> implements Handler<RpcResponse, RpcRequest> {
    private static  final Logger logger= LoggerFactory.getLogger(ClientHandlerMapping.class);
    private RpcResponse rpcResponse;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        this.rpcResponse = msg;
        latch.countDown();

    }


    public RpcResponse send(RpcRequest request,ChannelFuture future) {

        future.channel().writeAndFlush(request);

        try {

            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(logger.isInfoEnabled())logger.info(rpcResponse.toString());
        return rpcResponse;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        cause.printStackTrace();
        ctx.close();
    }



    @Override
    public RpcResponse handle(RpcRequest rpcRequest) {
        return null;
    }


}
