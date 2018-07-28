package com.jy.im.common.entity;

import com.jy.im.common.constants.CommonMessageCode;
import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageContentType;

public class ResponsiveMessage extends BaseMessage {

    protected int messageId;

    private CommonMessageCode errorCode;

    public CommonMessageCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(CommonMessageCode errorCode) {
        this.errorCode = errorCode;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public ResponsiveMessage(MessageProtocol protocol, MessageContentType messageContentType) {
        super(protocol, messageContentType);
    }
}
