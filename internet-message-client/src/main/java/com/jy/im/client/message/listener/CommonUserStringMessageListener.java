package com.jy.im.client.message.listener;

import com.jy.im.base.component.daemon.client.AbstractMessageListener;
import com.jy.im.common.entity.CommonUserStringMessage;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class CommonUserStringMessageListener extends AbstractMessageListener<CommonUserStringMessage> {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(CommonUserStringMessageListener.class);

    @Override
    public void callback(CommonUserStringMessage commonMessage) {
        logger.info("receive a common message");
    }

}
