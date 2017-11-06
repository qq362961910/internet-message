package com.jy.im.base.component.launcher;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class DefaultLauncherListener implements LauncherListener {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(DefaultLauncherListener.class);

    @Override
    public void startup(AbstractLauncher launcher) {
        logger.info("DefaultLauncherListener run startup()....");
    }

    @Override
    public void close(AbstractLauncher launcher) {
        logger.info("DefaultLauncherListener run close()....");
    }
}
