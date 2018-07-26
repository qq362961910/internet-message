package com.jy.im.server.tcp.netty.writer;

import com.jy.im.base.component.writer.tcp.netty.NettyResponseMessageWriter;
import com.jy.im.common.entity.LoginFailResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class CommonLoginFailResponseMessageWriter extends NettyResponseMessageWriter<LoginFailResponseMessage> {

    @Override
    public void doEncode0(ChannelHandlerContext ctx, LoginFailResponseMessage msg, ByteBuf out) {
        //1. user id
        out.writeLong(msg.getUserId());
        //2. password
        out.writeBytes(msg.getPassword().getBytes());
    }
}
