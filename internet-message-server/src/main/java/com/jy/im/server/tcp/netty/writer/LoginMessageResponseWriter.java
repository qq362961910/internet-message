package com.jy.im.server.tcp.netty.writer;

import com.jy.im.base.component.writer.tcp.netty.NettyMessageWriter;
import com.jy.im.common.entity.LoginMessageResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class LoginMessageResponseWriter extends NettyMessageWriter<LoginMessageResponse> {

    @Override
    public void doEncode(ChannelHandlerContext ctx, LoginMessageResponse message, ByteBuf out) {
        //code(4)
        out.writeInt(message.getCode());
        //ticket(32)
        out.writeBytes(message.getTicket());
    }

}
