package com.jy.im.common.entity;

import com.jy.im.common.constants.ServerNotificationType;

/**
 * 服务器通知: ticket 无效
 * */
public class TicketInvalidServerNotificationMessage extends ServerNotificationMessage {

    public TicketInvalidServerNotificationMessage() {
        super(ServerNotificationType.TICKET_INVALID);
    }
}
