package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageContentType;

public class LoginRequestMessage extends RequestMessage {

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

    public LoginRequestMessage() {
        super(MessageProtocol.COMMON, MessageContentType.CLIENT_LOGIN);
    }
}
