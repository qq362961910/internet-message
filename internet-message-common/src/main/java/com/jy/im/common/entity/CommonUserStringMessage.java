package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageType;

import java.util.Arrays;

/**
 * 通用消息
 */
public class CommonUserStringMessage extends CommonUserMessage {

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

    public CommonUserStringMessage() {
        super(MessageType.STRING);
    }

    @Override
    public String toString() {
        return "CommonUserStringMessage{" +
            "content=" + Arrays.toString(content) +
            "} " + super.toString();
    }
}
