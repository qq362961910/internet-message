package com.jy.im.base.component.writer;

import com.jy.im.common.entity.LoginMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyLoginMessageWriter extends MessageToByteEncoder<LoginMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, LoginMessage msg, ByteBuf out) throws Exception {
        out.writeBytes(messageToBytes(msg));
    }

    public ByteBuf messageToBytes(LoginMessage message) {
        ByteBuf content = ByteBufAllocator.DEFAULT.buffer(3);
        //protocol(1)
        content.writeByte(message.getProtocol());
        //length(2)
        //type(1) + userId(8) + password(32) + code(0)
        short contentLength = 1 + 8 + 32 + 0;
        content.writeShort(contentLength);
        //type(1)
        content.writeByte(message.getMessageType());
        //userId(8)
        content.writeLong(message.getUserId());
        //password(32)
        content.writeBytes(message.getPassword());
        //code(0)
        //todo
        return content;
    }
}
