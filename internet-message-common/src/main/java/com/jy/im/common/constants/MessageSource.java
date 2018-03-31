package com.jy.im.common.constants;

/**
 * 消息ID类型
 */
public enum MessageSource {

    /**
     * 用户消息
     * */
    USER_ID_TYPE((byte) 0, "user"),

    /**
     * 群组消息
     * */
    GROUP_ID_TYPE((byte) 1, "group"),

    /**
     * 系统消息
     * */
    SYSTEM_ID_TYPE((byte) 2, "system"),;

    public final byte value;

    public final String name;

    public static MessageSource getMessageSource(byte value) {
        for (MessageSource source : values()) {
            if (source.value == value) {
                return source;
            }
        }
        return null;
    }

    MessageSource(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return "MessageSource{" +
            "value=" + value +
            ", name='" + name + '\'' +
            "} " + super.toString();
    }
}
