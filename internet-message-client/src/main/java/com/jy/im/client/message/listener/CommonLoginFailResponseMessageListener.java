package com.jy.im.client.message.listener;

import com.jy.im.base.component.daemon.client.AbstractMessageListener;
import com.jy.im.common.entity.CommonLoginFailResponseMessage;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class CommonLoginFailResponseMessageListener extends AbstractMessageListener<CommonLoginFailResponseMessage> {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(CommonLoginFailResponseMessageListener.class);

    @Override
    public void callback(CommonLoginFailResponseMessage commonLoginFailResponseMessage) {
        logger.warn("login fail, user id: {}, password: {}", commonLoginFailResponseMessage.getUserId(), commonLoginFailResponseMessage.getPassword());
    }
}
