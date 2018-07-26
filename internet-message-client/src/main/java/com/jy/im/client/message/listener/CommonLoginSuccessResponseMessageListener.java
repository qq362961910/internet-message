package com.jy.im.client.message.listener;

import com.jy.im.base.component.daemon.client.AbstractMessageListener;
import com.jy.im.client.tcp.netty.TcpCommonMessageClient;
import com.jy.im.common.entity.LoginSuccessResponseMessage;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class CommonLoginSuccessResponseMessageListener extends AbstractMessageListener<LoginSuccessResponseMessage> {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(CommonLoginSuccessResponseMessageListener.class);

    @Override
    public void callback(LoginSuccessResponseMessage loginMessageResponse) {
        logger.info("receive a CommonLoginResponseMessage, login success, ticket: {}", new String(loginMessageResponse.getTicket()));
        TcpCommonMessageClient.TICKET_MAP.put(loginMessageResponse.getUserId(), loginMessageResponse.getTicket());
    }
}
