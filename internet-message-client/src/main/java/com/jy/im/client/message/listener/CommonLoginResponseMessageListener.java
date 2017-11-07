package com.jy.im.client.message.listener;

import com.jy.im.base.component.daemon.client.MessageListener;
import com.jy.im.common.entity.CommonLoginResponseMessage;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class CommonLoginResponseMessageListener extends MessageListener<CommonLoginResponseMessage> {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(CommonLoginResponseMessageListener.class);

    @Override
    public void callback(CommonLoginResponseMessage loginMessageResponse) {
        logger.info("receive a message: {}", loginMessageResponse);
    }
}
