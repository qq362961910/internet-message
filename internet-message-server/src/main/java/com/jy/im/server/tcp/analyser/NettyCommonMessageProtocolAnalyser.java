package com.jy.im.server.tcp.analyser;

import com.jy.im.base.component.analyser.protocol.NettyMessageProtocolAnalyser;
import com.jy.im.common.constants.MessageProtocol;
import org.springframework.stereotype.Component;

/**
 * 字符串协议解析器
 * 测试时使用, telnet,或udp client发动字符串
 */
@Component
public class NettyCommonMessageProtocolAnalyser extends NettyMessageProtocolAnalyser {

    @Override
    public MessageProtocol getSupportMessageProtocol() {
        return MessageProtocol.COMMON;
    }

}
