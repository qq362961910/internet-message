package com.jy.im.base.component.exception;

/**
 * 不支持的消息类型异常
 * */
public class UnsupportedMessageContentTypeException extends ImException{

    public UnsupportedMessageContentTypeException(String message) {
        super(message);
    }
}
