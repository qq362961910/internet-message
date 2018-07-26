package com.jy.im.base.component.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLauncherListener implements LauncherListener {

    private static final Logger logger = LoggerFactory.getLogger(DefaultLauncherListener.class);

    @Override
    public void afterStartup(AbstractLauncher launcher) {
        logger.info("DefaultLauncherListener run afterStartup()....");
    }

    @Override
    public void afterClose(AbstractLauncher launcher) {
        logger.info("DefaultLauncherListener run afterClose()....");
    }
}
