package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageProtocol;

public class ResponsiveMessage extends BaseMessage {

    protected int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResponsiveMessage() {
        super(MessageProtocol.COMMON.value);
    }
}
