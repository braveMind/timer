package com.jun.timer.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * Created by jun
 * 17/7/5 下午2:43.
 * des:
 */
public class RpcEncoder extends MessageToByteEncoder {


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        byte[] data = SerializationUtils.serialize((Serializable) msg);
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
