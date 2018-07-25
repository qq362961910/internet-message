package com.jy.im.server.tcp.netty.writer;

import com.jy.im.base.component.writer.tcp.netty.NettyResponseMessageWriter;
import com.jy.im.common.entity.CommonLoginSuccessResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class CommonLoginSuccessResponseMessageWriter extends NettyResponseMessageWriter<CommonLoginSuccessResponseMessage> {

    @Override
    public void doEncode0(ChannelHandlerContext ctx, CommonLoginSuccessResponseMessage msg, ByteBuf out) {
        //1. user id
        out.writeLong(msg.getUserId());
        //2. ticket(32)
        out.writeBytes(msg.getTicket());
    }
}
