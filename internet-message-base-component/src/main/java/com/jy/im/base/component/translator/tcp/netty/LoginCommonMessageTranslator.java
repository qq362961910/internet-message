package com.jy.im.base.component.translator.tcp.netty;

import com.jy.im.base.component.translator.MessageTranslator;
import com.jy.im.common.constants.MessageType;
import com.jy.im.common.entity.LoginMessageRequest;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class LoginCommonMessageTranslator implements MessageTranslator<ByteBuf> {

    private static final List<MessageType> SUPPORT_MESSAGE_TYPES = new ArrayList<>();

    @Override
    public Object translate(ByteBuf in) {
        //1. userId
        long userId = in.readLong();
        //2.password
        byte[] password = new byte[32];
        in.readBytes(password);
        //3.code
        //todo
        //package the message
        LoginMessageRequest message = new LoginMessageRequest();
        message.setMessageType(MessageType.LOGIN.value);
        message.setUserId(userId);
        message.setPassword(password);
        return message;
    }

    @Override
    public boolean support(MessageType type) {
        return MessageType.LOGIN == type;
    }

    public LoginCommonMessageTranslator() {
        SUPPORT_MESSAGE_TYPES.add(MessageType.STRING);
    }
}
