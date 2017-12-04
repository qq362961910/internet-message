package com.jy.im.base.component.launcher;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class DefaultLauncherListener implements LauncherListener {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(DefaultLauncherListener.class);

    @Override
    public void afterStartup(AbstractLauncher launcher) {
        logger.info("DefaultLauncherListener run afterStartup()....");
    }

    @Override
    public void afterClose(AbstractLauncher launcher) {
        logger.info("DefaultLauncherListener run afterClose()....");
    }
}
