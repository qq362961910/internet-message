package com.jy.im.base.component.writer.tcp.netty;

import com.jy.im.common.entity.CommonUserMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class NettyCommonMessageWriter extends NettyMessageWriter<CommonUserMessage> {

    @Override
    public void doEncode(ChannelHandlerContext ctx, CommonUserMessage message, ByteBuf out) {
        //toId(8)
        out.writeLong(message.getToId());
        //toIdType(1)
        out.writeByte(message.getToIdType());
        //ticket(32)
        out.writeBytes(message.getTicket());
        //content.length(2)
        out.writeShort(message.getContent().length);
        //content(content.length)
        out.writeBytes(message.getContent());
    }
}
