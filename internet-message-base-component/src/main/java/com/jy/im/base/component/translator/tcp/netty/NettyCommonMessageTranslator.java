package com.jy.im.base.component.translator.tcp.netty;

import com.jy.im.base.component.translator.NettyAbstractMessageTranslator;
import com.jy.im.common.constants.MessageProtocol;

public abstract class NettyCommonMessageTranslator extends NettyAbstractMessageTranslator {

    @Override
    public MessageProtocol supportMessageProtocol() {
        return MessageProtocol.COMMON;
    }
}
