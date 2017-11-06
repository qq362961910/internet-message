package com.jy.im.common.constants;

public enum ResponseCode {

    /**
     * SUCCESS
     */
    SUCCESS(200, "SUCCESS"),

    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(101, "用户名或密码错误"),;
    public final int value;

    public final String name;

    public static ResponseCode getResponseCode(int value) {
        for (ResponseCode code : values()) {
            if (code.value == value) {
                return code;
            }
        }
        return null;
    }

    ResponseCode(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
            "value=" + value +
            ", name='" + name + '\'' +
            "} " + super.toString();
    }
}
