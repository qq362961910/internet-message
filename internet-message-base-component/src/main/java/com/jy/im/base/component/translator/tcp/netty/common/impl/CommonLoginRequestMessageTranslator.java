package com.jy.im.base.component.translator.tcp.netty.common.impl;

import com.jy.im.base.component.translator.tcp.netty.common.NettyCommonRequestMessageTranslator;
import com.jy.im.common.constants.MessageContentType;
import com.jy.im.common.entity.LoginRequestMessage;
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
        LoginRequestMessage message = new LoginRequestMessage();
        message.setMessageId(messageId);
        message.setUserId(userId);
        message.setPassword(password);
        return message;
    }

    @Override
    public boolean support(MessageContentType type) {
        return MessageContentType.CLIENT_LOGIN == type;
    }

}
