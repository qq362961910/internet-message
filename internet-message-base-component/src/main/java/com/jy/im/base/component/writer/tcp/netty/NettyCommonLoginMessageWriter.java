package com.jy.im.base.component.writer.tcp.netty;

import com.jy.im.common.entity.CommonLoginRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class NettyCommonLoginMessageWriter extends NettyMessageWriter<CommonLoginRequestMessage> {

    @Override
    public void doEncode(ChannelHandlerContext ctx, CommonLoginRequestMessage message, ByteBuf out) {
        //userId(8)
        out.writeLong(message.getUserId());
        //password(32)
        out.writeBytes(message.getPassword());
        //code(0)
        //todo
    }

}
