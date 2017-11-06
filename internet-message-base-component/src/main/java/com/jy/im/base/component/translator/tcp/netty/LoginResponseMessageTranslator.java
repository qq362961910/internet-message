package com.jy.im.base.component.translator.tcp.netty;

import com.jy.im.base.component.translator.MessageTranslator;
import com.jy.im.common.constants.MessageType;
import com.jy.im.common.constants.ResponseCode;
import com.jy.im.common.entity.LoginMessageResponse;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class LoginResponseMessageTranslator implements MessageTranslator<ByteBuf> {

    private static final List<MessageType> SUPPORT_MESSAGE_TYPES = new ArrayList<>();

    @Override
    public Object translate(ByteBuf in) {
        LoginMessageResponse message = new LoginMessageResponse();
        //1. code
        message.setCode(in.readInt());
        if (message.getCode() == ResponseCode.SUCCESS.value) {
            //2.ticket
            byte[] ticket = new byte[32];
            in.readBytes(ticket);
            message.setTicket(ticket);
        }

        return message;
    }

    @Override
    public boolean support(MessageType type) {
        return MessageType.LOGIN_RESPONSE == type;
    }

    public LoginResponseMessageTranslator() {
        SUPPORT_MESSAGE_TYPES.add(MessageType.STRING);
    }
}
