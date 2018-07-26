package com.jy.im.base.component.translator.tcp.netty.common;

import com.jy.im.common.constants.MessageType;
import com.jy.im.common.constants.ServerNotificationType;
import io.netty.buffer.ByteBuf;

public abstract class NettyCommonServerNotificationMessageTranslator extends NettyCommonMessageTranslator {

    @Override
    public boolean support(MessageType type) {
        return MessageType.SERVER_NOTIFICATION == type;
    }

    @Override
    public Object translate(ByteBuf byteBuf) {
        return doTranslate(ServerNotificationType.getServerNotificationType(byteBuf.readByte()), byteBuf);
    }

    protected abstract Object doTranslate(ServerNotificationType serverNotificationType, ByteBuf byteBuf);
}
