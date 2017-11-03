package com.jy.im.base.component.daemon.listener;

import com.jy.im.common.entity.CommonMessage;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class CommonMessageListener extends MessageListener<CommonMessage> {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(CommonMessageListener.class);

    @Override
    public void callback(CommonMessage commonMessage) {
        logger.info("receive a common message");
    }

}
