package com.jy.im.common.constants;

public enum  CommonMessageCode {

    SUCCESS(200),

    FAIL(400),

    USERNAME_OR_PASSWORD_ERROR(401),



    ;

    public final int value;

    CommonMessageCode(int value) {
        this.value = value;
    }
}
