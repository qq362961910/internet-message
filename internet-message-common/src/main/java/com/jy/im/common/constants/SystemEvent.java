package com.jy.im.common.constants;

public enum SystemEvent {

    /**
     * LOGIN_SUCCESS
     * */
    LOGIN_SUCCESS((byte)0, "login-success"),

    /**
     * LOGIN_FAIL
     * */
    LOGIN_FAIL((byte)1, "login-fail"),

    /**
     * 用户消息
     * */
    USER_MESSAGE((byte)2, "user-message"),

    /**
     * 群组消息
     * */
    GROUP_MESSAGE((byte)3, "group-message")
    ;

    /**
     * value
     * */
    public final byte value;

    /**
     * name
     * */
    public final String name;

    SystemEvent(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public static SystemEvent getSystemEvent(byte value) {
        for (SystemEvent event: values()) {
            if(event.value == value) {
                return event;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "SystemEvent{" +
            "value=" + value +
            ", name='" + name + '\'' +
            "} " + super.toString();
    }
}
