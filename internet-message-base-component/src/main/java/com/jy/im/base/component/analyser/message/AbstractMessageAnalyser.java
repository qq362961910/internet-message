package com.jy.im.base.component.analyser.message;

import com.jy.im.base.component.enums.MessageProtocol;

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
        boolean support = getProtocolAnalyser().support((In) in);
        if (support) {
            MessageProtocol messageProtocol = getProtocolAnalyser().analyser((In) in);
            if (doSupport(messageProtocol)) {
                return true;
            }
        }
        return false;
    }

    /**
     * to be implemented by sub class
     */
    public abstract boolean doSupport(MessageProtocol messageProtocol);

}
