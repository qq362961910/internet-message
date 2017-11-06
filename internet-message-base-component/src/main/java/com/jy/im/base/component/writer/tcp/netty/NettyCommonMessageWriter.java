package com.jy.im.base.component.writer.tcp.netty;

import com.jy.im.common.entity.CommonMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyCommonMessageWriter extends MessageToByteEncoder<CommonMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CommonMessage msg, ByteBuf out) throws Exception {
        out.writeBytes(messageToBytes(msg));
    }

    public ByteBuf messageToBytes(CommonMessage message) {
        ByteBuf content = ByteBufAllocator.DEFAULT.buffer(3);
        //protocol(1)
        content.writeByte(message.getProtocol());
        //length(2):
        short contentLength = (short) (1 + 8 + 1 + 32 + 2 + message.getContent().length);
        content.writeShort(contentLength);
        //type(1)
        content.writeByte(message.getMessageType());
        //toId(8)
        content.writeLong(message.getToId());
        //toIdType(1)
        content.writeByte(message.getToIdType());
        //ticket(32)
        content.writeBytes(message.getTicket());
        //content.length(2)
        content.writeShort(message.getContent().length);
        //content(content.length)
        content.writeBytes(message.getContent());
        return content;
    }
}
