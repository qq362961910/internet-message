package com.jy.im.base.component.translator.tcp.netty.common;

import com.jy.im.base.component.translator.tcp.netty.NettyCommonMessageTranslator;
import com.jy.im.common.constants.CommonMessageCode;
import com.jy.im.common.constants.MessageType;
import com.jy.im.common.entity.CommonLoginResponseMessage;
import io.netty.buffer.ByteBuf;

public class CommonLoginResponseMessageTranslator extends NettyCommonMessageTranslator {

    @Override
    public Object translate(ByteBuf in) {
        CommonLoginResponseMessage message = new CommonLoginResponseMessage();
        //1. code
        message.setCode(in.readInt());
        if (message.getCode() == CommonMessageCode.SUCCESS.value) {
            //2.user id
            message.setUserId(in.readLong());
            //3.ticket
            byte[] ticket = new byte[32];
            in.readBytes(ticket);
            message.setTicket(ticket);
        }

        return message;
    }

    @Override
    public boolean support(MessageType type) {
        return MessageType.CONNECT == type;
    }

}
