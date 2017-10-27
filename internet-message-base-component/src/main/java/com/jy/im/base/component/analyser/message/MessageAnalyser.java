package com.jy.im.base.component.analyser.message;

import com.jy.im.base.component.analyser.protocol.ProtocolAnalyser;

/**
 * 消息解析器
 * In 原始入参
 * Out 入参消息内容
 *
 * 因为入参必须一致,如果不一致即使解析出的协议是消息解析器支持的,消息解析器也不能处理,所以协议解析器与消息解析器一对一匹配
 * 每个消息解析器应该拥有一个消息协议解析器用来解析出协议类型
 *
 * */
public interface MessageAnalyser<In, Out> {

    /**
     * 是否支持解析该协议
     * 首先调用协议解析器判断是否支持该入参类型, 如果支持,解析出协议判断消息解析器是否支持该协议解析
     * */
    boolean support(Object in);

    /**
     * 解析消息
     * */
    Out analyse(In t);

    /**
     * 设置消息协议解析器
     * */
    void setProtocolAnalyser(ProtocolAnalyser<In> protocolAnalyser);

}
