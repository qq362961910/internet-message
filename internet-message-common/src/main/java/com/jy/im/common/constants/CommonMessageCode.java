package com.jy.im.common.constants;

public enum  CommonMessageCode {

    /**
     * SUCCESS
     * */
    SUCCESS(200),

    /**
     * FAIL
     * */
    FAIL(400),

    /**
     * 用户名或密码正确
     * */
    USERNAME_OR_PASSWORD_ERROR(401),

    ;

    public final int value;

    CommonMessageCode(int value) {
        this.value = value;
    }

    public static CommonMessageCode getCommonMessageCode(int value) {
        for(CommonMessageCode code: values()) {
            if(code.value == value) {
                return code;
            }
        }
        return null;
    }
}
