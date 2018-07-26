package com.jy.im.client.message.listener;

import com.jy.im.base.component.daemon.client.AbstractMessageListener;
import com.jy.im.common.entity.LoginFailResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonLoginFailResponseMessageListener extends AbstractMessageListener<LoginFailResponseMessage> {

    private static final Logger logger = LoggerFactory.getLogger(CommonLoginFailResponseMessageListener.class);

    @Override
    public void callback(LoginFailResponseMessage loginFailResponseMessage) {
        logger.warn("login fail, user id: {}, password: {}", loginFailResponseMessage.getUserId(), loginFailResponseMessage.getPassword());
    }
}
