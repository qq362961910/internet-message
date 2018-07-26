package com.jy.im.base.component.translator.tcp.netty.common;

import com.jy.im.common.constants.CommonMessageCode;
import io.netty.buffer.ByteBuf;

public abstract class NettyCommonResponseMessageTranslator extends NettyCommonMessageTranslator {

    @Override
    public Object translate(ByteBuf byteBuf) {
        return doTranslate(byteBuf.readInt(), CommonMessageCode.getCommonMessageCode(byteBuf.readInt()), byteBuf);
    }

    public abstract Object doTranslate(int messageId, CommonMessageCode code, ByteBuf byteBuf);
}
