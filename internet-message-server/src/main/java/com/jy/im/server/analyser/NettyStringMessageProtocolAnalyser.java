package com.jy.im.server.analyser;

import com.jy.im.base.component.analyser.protocol.AbstractMessageProtocolAnalyser;
import com.jy.im.base.component.enums.MessageProtocol;
import io.netty.buffer.ByteBuf;

/**
 * 字符串协议解析器
 * 测试时使用, telnet,或udp client发动字符串
 */
public class NettyStringMessageProtocolAnalyser extends AbstractMessageProtocolAnalyser<ByteBuf> {
    @Override
    public MessageProtocol analyser(ByteBuf byteBuf) {
        return MessageProtocol.STRING;
    }
}
