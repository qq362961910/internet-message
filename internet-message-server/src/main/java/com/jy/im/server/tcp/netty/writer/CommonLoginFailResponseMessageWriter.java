package com.jy.im.server.tcp.netty.writer;

import com.jy.im.base.component.writer.tcp.netty.NettyResponseMessageWriter;
import com.jy.im.common.entity.CommonLoginFailResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class CommonLoginFailResponseMessageWriter extends NettyResponseMessageWriter<CommonLoginFailResponseMessage> {

    @Override
    public void doEncode0(ChannelHandlerContext ctx, CommonLoginFailResponseMessage msg, ByteBuf out) {
        //1. user id
        out.writeLong(msg.getUserId());
        //2. password
        out.writeBytes(msg.getPassword().getBytes());
    }
}
