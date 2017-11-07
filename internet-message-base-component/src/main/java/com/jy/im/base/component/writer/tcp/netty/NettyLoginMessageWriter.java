package com.jy.im.base.component.writer.tcp.netty;

import com.jy.im.common.entity.LoginMessageRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class NettyLoginMessageWriter extends NettyMessageWriter<LoginMessageRequest> {

    @Override
    public void doEncode(ChannelHandlerContext ctx, LoginMessageRequest message, ByteBuf out) {
        //userId(8)
        out.writeLong(message.getUserId());
        //password(32)
        out.writeBytes(message.getPassword());
        //code(0)
        //todo
    }
}
