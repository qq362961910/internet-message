package com.jy.im.base.component.analyser.message.tcp.netty;

import com.jy.im.base.component.analyser.protocol.netty.NettyCommonMessageProtocolAnalyser;

/**
 * netty common message server
 * */
public class NettyServerMessageAnalyser extends NettyCommonMessageAnalyser {

    public NettyServerMessageAnalyser() {
        //指定入參类型和协议
        setNettyMessageProtocolAnalyser(new NettyCommonMessageProtocolAnalyser());
    }

}
