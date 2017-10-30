package com.jy.im.base.component.analyser.message;

import com.jy.im.base.component.analyser.protocol.ProtocolAnalyser;

/**
 * 抽象消息解析器
 */
public abstract class AbstractMessageAnalyser<In> implements MessageAnalyser<In> {

    /**
     * 消息协议解析器判断是否支持入参
     * 消息解析器判断是否支持该协议
     */
    @Override
    public boolean support(In in) {
        return getProtocolAnalyser().support(in);
    }

    /**
     * 一个消息解析器捆绑一个协议解析器(限定协议解析器和消息解析器入参类型一致)
     * 1.限定入参类型
     * 2.限定协议类型
     */
    public abstract ProtocolAnalyser<In> getProtocolAnalyser();

}
