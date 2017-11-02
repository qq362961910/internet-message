package com.jy.im.base.component.daemon.client.writer;

import com.jy.im.common.entity.CommonMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CommonMessageWriter extends MessageToByteEncoder<CommonMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CommonMessage msg, ByteBuf out) throws Exception {
        out.writeByte(msg.getMessageType());
        out.writeShort(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
