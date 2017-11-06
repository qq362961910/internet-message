package com.jy.im.base.component.translator.tcp.netty;

import com.jy.im.base.component.translator.MessageTranslator;
import com.jy.im.common.constants.MessageType;
import com.jy.im.common.entity.CommonMessage;
import io.netty.buffer.ByteBuf;

public class CommonMessageMessageTranslator implements MessageTranslator<ByteBuf> {

    @Override
    public Object translate(ByteBuf in) {
        //package the message
        CommonMessage message = new CommonMessage();
        //toId(8)
        message.setToId(in.readLong());
        //toIdType(1)
        message.setFromIdType(in.readByte());
        //ticket(32)
        byte[] ticket = new byte[32];
        in.readBytes(ticket);
        message.setTicket(ticket);
        short contentLength = in.readShort();
        byte[] content = new byte[contentLength];
        in.readBytes(content);
        message.setContent(content);
        return message;
    }

    @Override
    public boolean support(MessageType type) {
        return MessageType.STRING == type;
    }
}
