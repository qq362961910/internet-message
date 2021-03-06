package com.jy.im.common.entity;

import com.jy.im.common.constants.MessageProtocol;
import com.jy.im.common.constants.MessageContentType;
import com.jy.im.common.constants.ServerNotificationType;

public abstract class ServerNotificationMessage extends BaseMessage {

    private ServerNotificationType serverNotificationType;

    public ServerNotificationType getServerNotificationType() {
        return serverNotificationType;
    }

    public void setServerNotificationType(ServerNotificationType serverNotificationType) {
        this.serverNotificationType = serverNotificationType;
    }

    public ServerNotificationMessage(ServerNotificationType serverNotificationType) {
        super(MessageProtocol.COMMON, MessageContentType.SERVER_NOTIFICATION);
        this.serverNotificationType = serverNotificationType;
    }


}
