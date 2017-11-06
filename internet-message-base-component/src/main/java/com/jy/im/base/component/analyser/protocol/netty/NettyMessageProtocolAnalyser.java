package com.jy.im.base.component.analyser.protocol.netty;

import com.jy.im.base.component.analyser.protocol.AbstractMessageProtocolAnalyser;
import com.jy.im.common.constants.MessageProtocol;
import io.netty.buffer.ByteBuf;

//指定入參类型
//同一种入參可支持不同的协议
public abstract class NettyMessageProtocolAnalyser extends AbstractMessageProtocolAnalyser<ByteBuf> {

    @Override
    public MessageProtocol analyser(ByteBuf byteBuf) {
        byte p = byteBuf.readByte();
        return MessageProtocol.getMessageProtocol(p);
    }
}
