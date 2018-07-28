package com.jy.im.base.component.translator.tcp.netty.common.impl;

import com.jy.im.base.component.translator.tcp.netty.common.NettyCommonResponseMessageTranslator;
import com.jy.im.common.constants.CommonMessageCode;
import com.jy.im.common.constants.MessageContentType;
import com.jy.im.common.entity.LoginFailResponseMessage;
import com.jy.im.common.entity.LoginSuccessResponseMessage;
import io.netty.buffer.ByteBuf;

public class CommonLoginResponseMessageTranslator extends NettyCommonResponseMessageTranslator {

    @Override
    public Object doTranslate(int messageId, CommonMessageCode code, ByteBuf in) {
        //SUCCESS
        if(CommonMessageCode.SUCCESS == code) {
            LoginSuccessResponseMessage successResponseMessage = new LoginSuccessResponseMessage();
            successResponseMessage.setMessageId(messageId);
            successResponseMessage.setErrorCode(code);
            //2.user id
            successResponseMessage.setUserId(in.readLong());
            //3.ticket
            byte[] ticket = new byte[32];
            in.readBytes(ticket);
            successResponseMessage.setTicket(ticket);
            return successResponseMessage;
        } else {
            //FAIL
            LoginFailResponseMessage failResponseMessage = new LoginFailResponseMessage();
            failResponseMessage.setMessageId(messageId);
            failResponseMessage.setErrorCode(code);
            failResponseMessage.setUserId(in.readLong());
            byte[] password = new byte[in.readableBytes()];
            in.readBytes(password);
            failResponseMessage.setPassword(new String(password));
            return failResponseMessage;
        }
    }

    @Override
    public boolean support(MessageContentType type) {
        return MessageContentType.SERVER_LOGIN_SUCCESS == type || MessageContentType.SERVER_LOGIN_FAIL == type;
    }

}
