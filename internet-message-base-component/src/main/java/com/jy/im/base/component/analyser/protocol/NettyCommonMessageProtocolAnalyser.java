package com.jy.im.base.component.analyser.protocol;

import com.jy.im.common.constants.MessageProtocol;

/**
 * 字符串协议解析器
 * 测试时使用, telnet,或udp client发动字符串
 */
public class NettyCommonMessageProtocolAnalyser extends NettyMessageProtocolAnalyser {

    @Override
    public MessageProtocol getSupportMessageProtocol() {
        return MessageProtocol.COMMON;
    }

}
