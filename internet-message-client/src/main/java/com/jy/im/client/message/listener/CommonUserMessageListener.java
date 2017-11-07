package com.jy.im.client.message.listener;

import com.jy.im.base.component.daemon.client.MessageListener;
import com.jy.im.common.entity.CommonUserMessage;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class CommonUserMessageListener extends MessageListener<CommonUserMessage> {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(CommonUserMessageListener.class);

    @Override
    public void callback(CommonUserMessage commonMessage) {
        logger.info("receive a common message");
    }

}
