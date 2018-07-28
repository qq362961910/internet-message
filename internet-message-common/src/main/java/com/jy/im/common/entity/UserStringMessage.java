package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageContentType;

import java.util.Arrays;

/**
 * 通用消息
 */
public class UserStringMessage extends UserMessage {

    /**
     * 消息内容
     */
    private byte[] content;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public UserStringMessage() {
        super(MessageContentType.STRING);
    }

    @Override
    public String toString() {
        return "CommonUserStringMessage{" +
            "content=" + Arrays.toString(content) +
            "} " + super.toString();
    }
}
