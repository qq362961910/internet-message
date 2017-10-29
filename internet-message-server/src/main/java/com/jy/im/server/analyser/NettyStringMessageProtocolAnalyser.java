package com.jy.im.server.analyser;

import com.jy.im.base.component.analyser.protocol.NettyMessageProtocolAnalyser;
import com.jy.im.base.component.enums.MessageProtocol;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

/**
 * 字符串协议解析器
 * 测试时使用, telnet,或udp client发动字符串
 */
@Component
public class NettyStringMessageProtocolAnalyser extends NettyMessageProtocolAnalyser {

    @Override
    public MessageProtocol analyser(ByteBuf byteBuf) {
        return MessageProtocol.STRING;
    }

    @Override
    public MessageProtocol getSupportMessageProtocol() {
        return MessageProtocol.STRING;
    }

}
