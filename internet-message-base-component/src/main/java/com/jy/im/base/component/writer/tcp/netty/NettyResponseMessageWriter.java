package com.jy.im.base.component.writer.tcp.netty;

import com.jy.im.common.entity.ResponsiveMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class NettyResponseMessageWriter<Message extends ResponsiveMessage> extends NettyMessageWriter<Message> {

    @Override
    public void doEncode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
        //1. message id
        out.writeInt(msg.getMessageId());
        //2. code
        out.writeInt(msg.getErrorCode().value);
        doEncode0(ctx, msg, out);
    }

    public abstract void doEncode0(ChannelHandlerContext ctx, Message msg, ByteBuf out);
}
