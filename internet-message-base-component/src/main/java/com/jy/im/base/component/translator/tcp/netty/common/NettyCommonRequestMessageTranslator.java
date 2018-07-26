package com.jy.im.base.component.translator.tcp.netty.common;

import io.netty.buffer.ByteBuf;

public abstract class NettyCommonRequestMessageTranslator extends NettyCommonMessageTranslator {

    @Override
    public Object translate(ByteBuf byteBuf) {
        return doTranslate(byteBuf.readInt(), byteBuf);
    }

    public abstract Object doTranslate(int messageId, ByteBuf byteBuf);
}
