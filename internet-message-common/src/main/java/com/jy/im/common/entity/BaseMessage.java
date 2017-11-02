package com.jy.im.common.entity;

public class BaseMessage {

    /**
     * 消息类型
     * */
    protected byte messageType;

    /**
     * length
     * */
    protected short length;


    public byte getMessageType() {
        return messageType;
    }

    public void setMessageType(byte messageType) {
        this.messageType = messageType;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }
}
