package com.jy.im.common.constants;

public enum MessageContentType {

    /**
     * STRING
     */
    STRING((byte) 0, "STRING"),

    /**
     * IMAGE
     */
    IMAGE((byte) 1, "IMAGE"),

    /**
     * VOICE
     */
    VOICE((byte) 2, "VOICE"),

    /**
     * VIDEO
     */
    VIDEO((byte) 3, "VOICE"),

    /**
     * FILE
     */
    FILE((byte) 4, "FILE"),

    /**
     * EVENT
     */
    EVENT((byte) 5, "EVENT"),

    /**
     * CLIENT LOGIN
     */
    CLIENT_LOGIN((byte) 123, "CLIENT_LOGIN"),

    /**
     * SERVER LOGIN SUCCESS
     * */
    SERVER_LOGIN_SUCCESS((byte) 124, "SERVER_LOGIN_SUCCESS"),

    /**
     * SERVER LOGIN FAIL
     * */
    SERVER_LOGIN_FAIL((byte) -124, "SERVER_LOGIN_FAIL"),

    /**
     * CLIENT LOGOUT
     */
    CLIENT_LOGOUT((byte) 125, "CLIENT_LOGOUT"),

    /**
     * SERVER LOGOUT SUCCESS
     */
    SERVER_LOGOUT_SUCCESS((byte) 126, "SERVER_LOGOUT_SUCCESS"),

    /**
     * SERVER LOGOUT FAIL
     */
    SERVER_LOGOUT_FAIL((byte) -126, "SERVER_LOGOUT_FAIL"),

    /**
     * SERVER_NOTIFICATION
     * */
    SERVER_NOTIFICATION((byte)127,"SERVER_NOTIFICATION");


    public final byte value;
    public final String name;

    public static MessageContentType getMessageContentType(byte value) {
        for (MessageContentType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }

    MessageContentType(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return "MessageContentType{" +
            "value=" + value +
            ", name='" + name + '\'' +
            '}';
    }
}
