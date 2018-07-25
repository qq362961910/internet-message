package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageType;

public class CommonLoginSuccessResponseMessage extends ResponsiveMessage {

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

    public CommonLoginSuccessResponseMessage() {
        super(MessageProtocol.COMMON, MessageType.SERVER_LOGIN_SUCCESS);
    }
}
