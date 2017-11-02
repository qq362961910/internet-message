package com.jy.im.common.entity;

import com.jy.im.common.constants.CommonMessageType;

import java.util.Arrays;

public class SystemMessage extends BaseMessage {

    private byte event;

    private byte[] content;

    public byte getEvent() {
        return event;
    }

    public void setEvent(byte event) {
        this.event = event;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public SystemMessage() {
        messageType = CommonMessageType.LOGIN.value;
    }

    @Override
    public String toString() {
        return "SystemMessage{" +
            "event=" + event +
            ", content=" + Arrays.toString(content) +
            "} " + super.toString();
    }
}
