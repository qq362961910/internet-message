package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageContentType;

public class BaseMessage {

    /**
     * 协议
     * */
    private final byte protocol;
    /**
     * 消息类型
     * 1 byte
     */
    protected final byte messageContentType;

    public byte getProtocol() {
        return protocol;
    }

    public byte getMessageContentType() {
        return messageContentType;
    }

    public BaseMessage(MessageProtocol protocol, MessageContentType messageContentType) {
        this.protocol = protocol.value;
        this.messageContentType = messageContentType.value;
    }
}
