package com.jy.im.common.entity;

public class BaseMessage {

    /**
     * 协议
     * 1 byte
     */
    protected byte protocol;
    /**
     * length
     * 2 bytes
     */
    protected short length;

    /**
     * 消息类型
     * 1 byte
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

    public BaseMessage(byte protocol) {
        this.protocol = protocol;
    }
}
