package com.jy.im.base.component.analyser.message;

import com.jy.im.base.component.analyser.protocol.ProtocolAnalyser;
import com.jy.im.base.component.enums.MessageProtocol;

/**
 * 抽象消息解析器
 * */
public abstract class AbstractMessageAnalyser<In, Out> implements MessageAnalyser<In, Out> {

    private ProtocolAnalyser<In> protocolAnalyser;

    /**
     * 消息协议解析器判断是否支持入参
     * 消息解析器判断是否支持该协议
     * */
    @Override
    public boolean support(Object in) {
        boolean support = protocolAnalyser.support((In)in);
        if (support) {
            MessageProtocol messageProtocol = protocolAnalyser.analyser((In)in);
            if (doSupport(messageProtocol)) {
                return true;
            }
        }
        return false;
    }

    /**
     * to be implemented by sub class
     * */
    public abstract boolean doSupport(MessageProtocol messageProtocol);

    @Override
    public void setProtocolAnalyser(ProtocolAnalyser<In> protocolAnalyser) {
        this.protocolAnalyser = protocolAnalyser;
    }
}
