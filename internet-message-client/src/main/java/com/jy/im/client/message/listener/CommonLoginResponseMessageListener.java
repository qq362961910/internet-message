package com.jy.im.client.message.listener;

import com.jy.im.base.component.daemon.client.AbstractMessageListener;
import com.jy.im.client.tcp.netty.TcpCommonMessageClient;
import com.jy.im.common.constants.CommonMessageCode;
import com.jy.im.common.entity.CommonLoginResponseMessage;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class CommonLoginResponseMessageListener extends AbstractMessageListener<CommonLoginResponseMessage> {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(CommonLoginResponseMessageListener.class);

    @Override
    public void callback(CommonLoginResponseMessage loginMessageResponse) {
        logger.info("receive a CommonLoginResponseMessage");
        if(loginMessageResponse.getCode() == CommonMessageCode.SUCCESS.value) {
            logger.info("login success, ticket: {}", new String(loginMessageResponse.getTicket()));
            TcpCommonMessageClient.TICKET_MAP.put(loginMessageResponse.getUserId(), loginMessageResponse.getTicket());
        }
    }
}
