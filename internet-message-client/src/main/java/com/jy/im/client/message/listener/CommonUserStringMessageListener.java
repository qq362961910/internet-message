package com.jy.im.client.message.listener;

import com.jy.im.base.component.daemon.client.AbstractMessageListener;
import com.jy.im.common.entity.UserStringMessage;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class CommonUserStringMessageListener extends AbstractMessageListener<UserStringMessage> {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(CommonUserStringMessageListener.class);

    @Override
    public void callback(UserStringMessage userStringMessage) {
        logger.info("receive a common message");
    }

}
