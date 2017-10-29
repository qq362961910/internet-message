package com.jy.im.base.component.analyser.message;

/**
 * 抽象消息解析器
 */
public abstract class AbstractMessageAnalyser<In, Out> implements MessageAnalyser<In, Out> {

    /**
     * 消息协议解析器判断是否支持入参
     * 消息解析器判断是否支持该协议
     */
    @Override
    public boolean support(Object in) {
        return getProtocolAnalyser().support(in);
    }

}
