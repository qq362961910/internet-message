package com.jy.im.base.component.analyser.protocol.netty;

import com.jy.im.common.constants.MessageProtocol;

/**
 * 指定支持的协议类型
 */
public class NettyCommonMessageProtocolAnalyser extends NettyMessageProtocolAnalyser {

    @Override
    public MessageProtocol getSupportMessageProtocol() {
        return MessageProtocol.COMMON;
    }

}
