package com.jy.im.common.entity;

/**
 *通用消息
 */
public class CommonMessage {

    /**
     * 消息类型
     * */
    private byte messageType;

    /**
     * length
     * */
    private short length;

    /**
     * 消息内容
     * */
    private byte[] content;

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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
