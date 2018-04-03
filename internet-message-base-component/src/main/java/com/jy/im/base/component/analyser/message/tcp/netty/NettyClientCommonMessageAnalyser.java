package com.jy.im.base.component.analyser.message.tcp.netty;

import com.jy.im.base.component.analyser.protocol.netty.NettyCommonMessageProtocolAnalyser;
import com.jy.im.base.component.translator.tcp.netty.common.CommonLoginResponseMessageTranslator;
import com.jy.im.base.component.translator.tcp.netty.common.CommonUserStringMessageTranslator;

/**
 * netty common message client
 * */
public class NettyClientCommonMessageAnalyser extends NettyCommonMessageAnalyser {

    public NettyClientCommonMessageAnalyser() {
        //指定入參类型和协议
        setNettyMessageProtocolAnalyser(new NettyCommonMessageProtocolAnalyser());
        //添加消息翻译器
        addMessageTranslator(new CommonLoginResponseMessageTranslator());
        addMessageTranslator(new CommonUserStringMessageTranslator());
    }
}
