package com.jy.im.common.entity;

public class BaseMessage {

    /**
     * 协议
     */
    protected byte protocol;
    /**
     * length
     */
    protected short length;

    /**
     * 消息类型
     */
    protected byte messageType;

    public byte getProtocol() {
        return protocol;
    }

    public void setProtocol(byte protocol) {
        this.protocol = protocol;
    }

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
