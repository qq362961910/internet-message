package com.jy.im.base.component.exception;

/**
 * MessageTranslator未找到异常
 * */
public class NoMessageTranslatorFoundException extends ImException{

    public NoMessageTranslatorFoundException(String message) {
        super(message);
    }
}
