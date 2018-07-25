package com.jy.im.base.component.translator.tcp.netty.common;

import com.jy.im.base.component.translator.tcp.netty.NettyCommonRequestMessageTranslator;
import com.jy.im.common.constants.MessageType;
import com.jy.im.common.entity.CommonLoginRequestMessage;
import io.netty.buffer.ByteBuf;

public class CommonLoginRequestMessageTranslator extends NettyCommonRequestMessageTranslator {

    @Override
    public Object doTranslate(int messageId, ByteBuf in) {
        //1. userId
        long userId = in.readLong();
        //2.password
        byte[] password = new byte[32];
        in.readBytes(password);
        //3.code
        //todo
        //package the message
        CommonLoginRequestMessage message = new CommonLoginRequestMessage();
        message.setMessageId(messageId);
        message.setUserId(userId);
        message.setPassword(password);
        return message;
    }

    @Override
    public boolean support(MessageType type) {
        return MessageType.CLIENT_LOGIN == type;
    }

}
