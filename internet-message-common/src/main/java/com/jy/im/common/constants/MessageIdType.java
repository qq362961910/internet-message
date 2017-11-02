package com.jy.im.common.constants;

/**
 * 消息ID类型
 */
public enum MessageIdType {

    USER_ID_TYPE((byte)0, "user"),

    GROUP_ID_TYPE((byte)1, "group"),

    SYSTEM_ID_TYPE((byte)2, "system"),
    ;

    public final byte value;

    public final String name;

    public static MessageIdType getMessageIdType(byte value) {
        for (MessageIdType type: values()) {
            if(type.value == value) {
                return type;
            }
        }
        return null;
    }

    MessageIdType(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return "MessageIdType{" +
            "value=" + value +
            ", name='" + name + '\'' +
            '}';
    }
}
