package com.jy.im.client.message.listener;

import com.jy.im.base.component.daemon.client.AbstractMessageListener;
import com.jy.im.common.entity.LoginFailResponseMessage;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class CommonLoginFailResponseMessageListener extends AbstractMessageListener<LoginFailResponseMessage> {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(CommonLoginFailResponseMessageListener.class);

    @Override
    public void callback(LoginFailResponseMessage loginFailResponseMessage) {
        logger.warn("login fail, user id: {}, password: {}", loginFailResponseMessage.getUserId(), loginFailResponseMessage.getPassword());
    }
}
