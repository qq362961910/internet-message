package com.jy.im.base.component.translator.tcp.netty.common;

import com.jy.im.base.component.translator.tcp.netty.NettyCommonResponseMessageTranslator;
import com.jy.im.common.constants.CommonMessageCode;
import com.jy.im.common.constants.MessageType;
import com.jy.im.common.entity.CommonLoginFailResponseMessage;
import com.jy.im.common.entity.CommonLoginSuccessResponseMessage;
import io.netty.buffer.ByteBuf;

public class CommonLoginResponseMessageTranslator extends NettyCommonResponseMessageTranslator {

    @Override
    public Object doTranslate(int messageId, CommonMessageCode code, ByteBuf in) {
        //SUCCESS
        if(CommonMessageCode.SUCCESS == code) {
            CommonLoginSuccessResponseMessage successResponseMessage = new CommonLoginSuccessResponseMessage();
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
            CommonLoginFailResponseMessage failResponseMessage = new CommonLoginFailResponseMessage();
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
    public boolean support(MessageType type) {
        return MessageType.SERVER_LOGIN_SUCCESS == type || MessageType.SERVER_LOGIN_FAIL == type;
    }

}
