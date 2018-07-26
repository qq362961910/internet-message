package com.jy.im.client.message.listener;

import com.jy.im.base.component.daemon.client.AbstractMessageListener;
import com.jy.im.client.tcp.netty.TcpCommonMessageClient;
import com.jy.im.common.entity.LoginSuccessResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonLoginSuccessResponseMessageListener extends AbstractMessageListener<LoginSuccessResponseMessage> {

    private Logger logger = LoggerFactory.getLogger(CommonLoginSuccessResponseMessageListener.class);

    @Override
    public void callback(LoginSuccessResponseMessage loginMessageResponse) {
        logger.info("receive a CommonLoginResponseMessage, login success, ticket: {}", new String(loginMessageResponse.getTicket()));
        TcpCommonMessageClient.TICKET_MAP.put(loginMessageResponse.getUserId(), loginMessageResponse.getTicket());
    }
}
