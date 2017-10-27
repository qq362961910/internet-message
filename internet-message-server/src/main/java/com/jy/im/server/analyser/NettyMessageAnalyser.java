package com.jy.im.server.analyser;

import com.jy.im.base.component.analyser.message.AbstractMessageAnalyser;
import com.jy.im.base.component.enums.MessageProtocol;
import io.netty.buffer.ByteBuf;

public abstract class NettyMessageAnalyser<Out> extends AbstractMessageAnalyser<ByteBuf, Out> {

    private MessageProtocol messageProtocol;

    @Override
    public boolean doSupport(MessageProtocol messageProtocol) {
        return messageProtocol == messageProtocol;
    }

    public NettyMessageAnalyser(MessageProtocol messageProtocol) {
        this.messageProtocol = messageProtocol;
    }

}
