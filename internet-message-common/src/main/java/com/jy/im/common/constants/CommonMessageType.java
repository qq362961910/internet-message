package com.jy.im.common.constants;

/**
 * 协议位占一个长度,一个厂商一个协议
 * 消息结构为: T(1 byte) L(2 bytes) V(XXX)
 * */
public enum CommonMessageType {

    /**
     * STRING
     */
    STRING((byte)0, "STRING"),

    /**
     * IMAGE
     * */
    IMAGE((byte)1, "IMAGE"),

    /**
     * VOICE
     * */
    VOICE((byte)2, "VOICE"),

    /**
     * VIDEO
     * */
    VIDEO((byte)3, "VOICE"),

    /**
     * FILE
     * */
    FILE((byte)4, "FILE"),

    /**
     * EVENT MESSAGE
     */
    EVENT_MESSAGE((byte)1, "EVENT_MESSAGE");


    public final byte value;
    public final String name;

    public static void main(String[] args) {
        System.out.println((char) 0xFA);
    }

    public static CommonMessageType getCommonMessageType(byte value) {
        for (CommonMessageType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }

    CommonMessageType(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return "CommonMessageType{" +
            "value=" + value +
            ", name='" + name + '\'' +
            '}';
    }
}
