package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageType;

public class BaseMessage {

    /**
     * 协议
     * */
    private final byte protocol;
    /**
     * 消息类型
     * 1 byte
     */
    protected final byte messageType;

    public byte getProtocol() {
        return protocol;
    }

    public byte getMessageType() {
        return messageType;
    }

    public BaseMessage(MessageProtocol protocol, MessageType messageType) {
        this.protocol = protocol.value;
        this.messageType = messageType.value;
    }
}
