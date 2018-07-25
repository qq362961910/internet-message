package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageType;

public class RequestMessage extends BaseMessage {

    private int messageId;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public RequestMessage(MessageProtocol protocol, MessageType messageType) {
        super(protocol, messageType);
    }

}
