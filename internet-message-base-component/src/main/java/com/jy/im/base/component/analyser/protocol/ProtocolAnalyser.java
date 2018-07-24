package com.jy.im.base.component.analyser.protocol;

import com.jy.im.common.constants.MessageProtocol;

/**
 * 消息协议解析器
 * In 原始入参
 */
public interface ProtocolAnalyser<In> {

    /**
     * 判断是否支持该入参
     */
    boolean support(In in);



    /**
     * 解析消息
     */
    MessageProtocol analyser(In in);

}
