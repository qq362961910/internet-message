package com.jy.im.base.component.exception;

/**
 * 不支持的消息类型异常
 * */
public class UnsupportedMessageTypeException extends ImException{

    public UnsupportedMessageTypeException(String message) {
        super(message);
    }
}
