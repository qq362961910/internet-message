package com.jy.im.client.message.listener;

import com.jy.im.base.component.daemon.client.AbstractMessageListener;
import com.jy.im.common.entity.TicketInvalidServerNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonTicketInvalidServerNotificationMessageListener extends AbstractMessageListener<TicketInvalidServerNotificationMessage> {

    private static final Logger logger = LoggerFactory.getLogger(CommonTicketInvalidServerNotificationMessageListener.class);

    @Override
    public void callback(TicketInvalidServerNotificationMessage ticketInvalidServerNotificationMessage) {
        logger.warn("ticket has been invalid");
    }
}
