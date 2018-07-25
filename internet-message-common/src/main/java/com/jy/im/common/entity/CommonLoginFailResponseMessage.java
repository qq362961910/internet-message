package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageType;

public class CommonLoginFailResponseMessage extends ResponsiveMessage {

    private long userId;

    private String password;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CommonLoginFailResponseMessage() {
        super(MessageProtocol.COMMON, MessageType.SERVER_LOGIN_FAIL);
    }
}
