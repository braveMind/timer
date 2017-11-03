package com.jun.timer.handler;

import com.iph.zhaopin.rpc.common.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;

/**
 * Created by jun
 * 17/7/5 下午1:43.
 * des:获取通道中的数据
 */
public class NettyChannelRead extends SimpleChannelInboundHandler {
    private static final Logger log= org.slf4j.LoggerFactory.getLogger(NettyChannelRead.class);

    private Handler handler;

    public NettyChannelRead(Handler handle) {
        this.handler = handle;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

      //  System.out.println(IPUtils.parseChannelRemoteAddr1(ctx.channel()));
        if(log.isInfoEnabled())log.info("client rpc call");
        RpcResponse rpcResponse= (RpcResponse) handler.handle(msg);
        ctx.writeAndFlush(rpcResponse);
    }


}
