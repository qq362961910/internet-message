package com.jy.im.common.entity;

import java.util.Arrays;

/**
 * 通用消息
 */
public class CommonMessage extends BaseMessage {

    /**
     * 消息内容
     */
    private byte[] content;

    /**
     * fromId
     */
    private long fromId;

    /**
     * fromIdType
     */
    private byte fromIdType;

    /**
     * toId
     */
    private long toId;

    /**
     * toIdType
     */
    private byte toIdType;

    /**
     * ticket
     */
    private byte[] ticket;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public byte getFromIdType() {
        return fromIdType;
    }

    public void setFromIdType(byte fromIdType) {
        this.fromIdType = fromIdType;
    }

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public byte getToIdType() {
        return toIdType;
    }

    public void setToIdType(byte toIdType) {
        this.toIdType = toIdType;
    }

    public byte[] getTicket() {
        return ticket;
    }

    public void setTicket(byte[] ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "CommonMessage{" +
            "content=" + Arrays.toString(content) +
            ", fromId=" + fromId +
            ", fromIdType=" + fromIdType +
            ", toId=" + toId +
            ", toIdType=" + toIdType +
            ", ticket=" + Arrays.toString(ticket) +
            "} " + super.toString();
    }
}
