package com.jy.im.base.component.analyser.protocol;

import com.jy.im.common.constants.MessageProtocol;
import io.netty.buffer.ByteBuf;

public abstract class NettyMessageProtocolAnalyser extends AbstractMessageProtocolAnalyser<ByteBuf> {

    @Override
    public MessageProtocol analyser(ByteBuf byteBuf) {
        byte p = byteBuf.readByte();
        return MessageProtocol.getMessageProtocol(p);
    }
}
