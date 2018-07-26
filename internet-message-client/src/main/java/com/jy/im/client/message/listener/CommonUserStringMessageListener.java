package com.jy.im.client.message.listener;

import com.jy.im.base.component.daemon.client.AbstractMessageListener;
import com.jy.im.common.entity.UserStringMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUserStringMessageListener extends AbstractMessageListener<UserStringMessage> {

    private Logger logger = LoggerFactory.getLogger(CommonUserStringMessageListener.class);

    @Override
    public void callback(UserStringMessage userStringMessage) {
        logger.info("receive a common message");
    }

}
