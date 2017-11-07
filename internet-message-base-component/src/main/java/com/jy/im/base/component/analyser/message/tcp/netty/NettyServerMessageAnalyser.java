package com.jy.im.base.component.analyser.message.tcp.netty;

import com.jy.im.base.component.analyser.protocol.netty.NettyCommonMessageProtocolAnalyser;
import com.jy.im.base.component.translator.tcp.netty.CommonUserMessageTranslator;
import com.jy.im.base.component.translator.tcp.netty.CommonLoginRequestMessageTranslator;

public class NettyServerMessageAnalyser extends NettyMessageAnalyser {

    public NettyServerMessageAnalyser() {
        //指定入參类型和协议
        setNettyMessageProtocolAnalyser(new NettyCommonMessageProtocolAnalyser());
        //添加消息翻译器
        addMessageTranslator(new CommonLoginRequestMessageTranslator(), new CommonUserMessageTranslator());
    }
}
