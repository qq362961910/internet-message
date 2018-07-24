package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageType;

public class CommonLoginResponseMessage extends ResponsiveMessage {

    private Long userId;

    private byte[] ticket;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public byte[] getTicket() {
        return ticket;
    }

    public void setTicket(byte[] ticket) {
        this.ticket = ticket;
    }

    public CommonLoginResponseMessage() {
        messageType = MessageType.CONNECT.value;
    }
}
