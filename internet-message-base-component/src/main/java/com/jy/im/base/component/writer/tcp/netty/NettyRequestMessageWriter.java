package com.jy.im.base.component.writer.tcp.netty;

import com.jy.im.common.entity.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class NettyRequestMessageWriter <Message extends RequestMessage> extends NettyMessageWriter<Message> {

    @Override
    public void doEncode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
        out.writeInt(msg.getMessageId());
        doEncode0(ctx, msg, out);
    }

    public abstract void doEncode0(ChannelHandlerContext ctx, Message msg, ByteBuf out);

}
