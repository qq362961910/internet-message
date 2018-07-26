package com.jy.im.base.component.writer.tcp.netty;

import com.jy.im.common.entity.ServerNotificationMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class NettyServerNotificationMessageWriter<Message extends ServerNotificationMessage> extends NettyMessageWriter<Message>{
    @Override
    public void doEncode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
        out.writeByte(msg.getServerNotificationType().value);
        doEncode0(ctx, msg, out);
    }

    protected abstract void doEncode0(ChannelHandlerContext ctx, Message msg, ByteBuf out);
}
