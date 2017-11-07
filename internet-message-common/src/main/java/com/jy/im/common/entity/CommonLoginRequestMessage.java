package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageType;

public class CommonLoginRequestMessage extends BaseMessage {

    /**
     * userId
     */
    private long userId;

    /**
     * password
     */
    private byte[] password;

    /**
     * code
     */
    private byte[] code;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getCode() {
        return code;
    }

    public void setCode(byte[] code) {
        this.code = code;
    }

    public CommonLoginRequestMessage() {
        messageType = MessageType.LOGIN.value;
    }
}
