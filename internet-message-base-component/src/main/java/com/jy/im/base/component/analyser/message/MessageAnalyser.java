package com.jy.im.base.component.analyser.message;

import com.jy.im.base.component.exception.NoMessageTranslatorFoundException;
import com.jy.im.base.component.exception.UnsupportedMessageContentTypeException;

/**
 * 消息解析器
 * In 原始入参
 */
public interface MessageAnalyser<In> {

    /**
     * 是否支持解析该协议
     * 首先调用协议解析器判断是否支持该入参类型, 如果支持,解析出协议判断消息解析器是否支持该协议解析
     */
    boolean support(In in);

    /**
     * 解析消息
     */
    Object analyse(In t) throws UnsupportedMessageContentTypeException, NoMessageTranslatorFoundException;

}
