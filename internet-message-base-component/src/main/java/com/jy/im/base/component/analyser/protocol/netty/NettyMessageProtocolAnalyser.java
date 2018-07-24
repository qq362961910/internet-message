package com.jy.im.base.component.analyser.protocol.netty;

import com.jy.im.base.component.analyser.protocol.AbstractMessageProtocolAnalyser;
import com.jy.im.common.constants.MessageProtocol;
import io.netty.buffer.ByteBuf;

//指定入參类型
public abstract class NettyMessageProtocolAnalyser extends AbstractMessageProtocolAnalyser<ByteBuf> {

    @Override
    public MessageProtocol analyser(ByteBuf byteBuf) {
        //mark current reader index
        byteBuf.markReaderIndex();
        MessageProtocol messageProtocol = doAnalyser(byteBuf);
        if(messageProtocol == null) {
            //if no messageProtocol found turn the reader index back to the old reader index
            byteBuf.resetReaderIndex();
        }
        return messageProtocol;
    }

    public abstract MessageProtocol doAnalyser(ByteBuf byteBuf);
}
