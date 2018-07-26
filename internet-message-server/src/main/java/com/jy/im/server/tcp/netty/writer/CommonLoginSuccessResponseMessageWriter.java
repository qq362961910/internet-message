package com.jy.im.server.tcp.netty.writer;

import com.jy.im.base.component.writer.tcp.netty.NettyResponseMessageWriter;
import com.jy.im.common.entity.LoginSuccessResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class CommonLoginSuccessResponseMessageWriter extends NettyResponseMessageWriter<LoginSuccessResponseMessage> {

    @Override
    public void doEncode0(ChannelHandlerContext ctx, LoginSuccessResponseMessage msg, ByteBuf out) {
        //1. user id
        out.writeLong(msg.getUserId());
        //2. ticket(32)
        out.writeBytes(msg.getTicket());
    }
}
