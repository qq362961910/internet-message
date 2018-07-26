package com.jy.im.base.component.writer.tcp.netty;

import com.jy.im.common.entity.LoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class NettyCommonLoginRequestMessageWriter extends NettyRequestMessageWriter<LoginRequestMessage> {

    @Override
    public void doEncode0(ChannelHandlerContext ctx, LoginRequestMessage message, ByteBuf out) {
        //userId(8)
        out.writeLong(message.getUserId());
        //password(32)
        out.writeBytes(message.getPassword());
        //code(0)
        //todo
    }
}
