package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageType;

public abstract class CommonUserMessage extends BaseMessage {

    /**
     * fromId
     */
    protected long fromId;

    /**
     * fromIdType
     */
    protected byte fromIdType;

    /**
     * toId
     */
    protected long toId;

    /**
     * toIdType
     */
    protected byte toIdType;

    /**
     * ticket
     */
    protected byte[] ticket;

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

    public CommonUserMessage(MessageType messageType) {
        super(MessageProtocol.COMMON, messageType);
    }
}
