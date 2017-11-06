package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageType;

public class LoginMessageResponse extends ResponsiveMessage {

    private byte[] ticket;

    public byte[] getTicket() {
        return ticket;
    }

    public void setTicket(byte[] ticket) {
        this.ticket = ticket;
    }

    public LoginMessageResponse() {
        messageType = MessageType.LOGIN_RESPONSE.value;
    }
}
