package com.jy.im.base.component.writer.tcp.netty;

import com.jy.im.common.entity.ServerNotificationMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class NettyCommonServerNotificationMessageWriter extends NettyServerNotificationMessageWriter<ServerNotificationMessage> {

    @Override
    public void doEncode0(ChannelHandlerContext ctx, ServerNotificationMessage msg, ByteBuf out) {
        ctx.flush();
    }
}
