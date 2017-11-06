package com.jy.im.server.tcp.netty.writer;

import com.jy.im.common.entity.LoginMessageResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class LoginMessageResponseWriter extends MessageToByteEncoder<LoginMessageResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, LoginMessageResponse msg, ByteBuf out) throws Exception {
        out.writeBytes(messageToBytes(msg));
    }

    private ByteBuf messageToBytes(LoginMessageResponse message) {
        ByteBuf content = ByteBufAllocator.DEFAULT.buffer(3);
        //protocol(1)
        content.writeByte(message.getProtocol());
        //length(2)
        //type(1) + ticket(32)
        short contentLength = 33;
        content.writeShort(contentLength);
        //type(1)
        content.writeByte(message.getMessageType());
        //code
        content.writeInt(message.getCode());
        //ticket(32)
        content.writeBytes(message.getTicket());
        return content;
    }
}
