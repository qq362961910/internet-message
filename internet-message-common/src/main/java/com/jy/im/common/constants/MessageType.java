package com.jy.im.common.constants;

public enum MessageType {

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
    SERVER_LOGOUT_FAIL((byte) -126, "SERVER_LOGOUT_FAIL");


    public final byte value;
    public final String name;

    public static void main(String[] args) {
        System.out.println((char) 0xFA);
    }

    public static MessageType getCommonMessageType(byte value) {
        for (MessageType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }

    MessageType(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return "MessageType{" +
            "value=" + value +
            ", name='" + name + '\'' +
            '}';
    }
}
