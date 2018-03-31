package com.jy.im.common.constants;

/**
 * 协议位占一个长度,一个厂商一个协议
 * 消息结构为: L(2 bytes) V(XXX)
 */
public enum MessageProtocol {

    /**
     * COMMON
     */
    COMMON((byte) 0, "COMMON");

    public final byte value;
    public final String name;

    public static MessageProtocol getMessageProtocol(byte value) {
        for (MessageProtocol protocol : values()) {
            if (protocol.value == value) {
                return protocol;
            }
        }
        return null;
    }

    MessageProtocol(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return "MessageProtocol{" +
            "value=" + value +
            ", name='" + name + '\'' +
            '}';
    }
}
