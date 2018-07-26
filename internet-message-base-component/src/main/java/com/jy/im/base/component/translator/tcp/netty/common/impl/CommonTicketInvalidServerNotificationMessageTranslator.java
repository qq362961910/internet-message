package com.jy.im.base.component.translator.tcp.netty.common.impl;

import com.jy.im.base.component.translator.tcp.netty.common.NettyCommonServerNotificationMessageTranslator;
import com.jy.im.common.constants.ServerNotificationType;
import com.jy.im.common.entity.TicketInvalidServerNotificationMessage;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonTicketInvalidServerNotificationMessageTranslator extends NettyCommonServerNotificationMessageTranslator {

    private static final Logger logger = LoggerFactory.getLogger(CommonTicketInvalidServerNotificationMessageTranslator.class);

    @Override
    protected Object doTranslate(ServerNotificationType serverNotificationType, ByteBuf byteBuf) {
        if(ServerNotificationType.TICKET_INVALID == serverNotificationType) {
            return new TicketInvalidServerNotificationMessage();
        } else {
            logger.error("unhandled notification type: {}", serverNotificationType);
            return null;
        }
    }
}
